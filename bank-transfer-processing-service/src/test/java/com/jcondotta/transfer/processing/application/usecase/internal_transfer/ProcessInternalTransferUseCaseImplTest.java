package com.jcondotta.transfer.processing.application.usecase.internal_transfer;

import com.jcondotta.test_support.clock.TestClockExamples;
import com.jcondotta.test_support.iban.TestIbanExamples;
import com.jcondotta.transfer.application.ports.output.banking.LookupBankAccountFacade;
import com.jcondotta.transfer.application.ports.output.repository.BankTransferRepository;
import com.jcondotta.transfer.application.usecase.process_internal_transfer.ProcessInternalTransferUseCase;
import com.jcondotta.transfer.application.usecase.process_internal_transfer.model.CreateInternalTransferFromAccountIdToIbanCommand;
import com.jcondotta.transfer.domain.bank_account.entity.BankAccount;
import com.jcondotta.transfer.domain.bank_account.exceptions.BankAccountNotFoundException;
import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;
import com.jcondotta.transfer.domain.banktransfer.entity.BankTransfer;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalIbanRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.identifier.InternalAccountIbanIdentifier;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.identifier.InternalAccountIdIdentifier;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.transfer_entry.InternalTransferEntry;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessInternalTransferUseCaseImplTest {

    private static final BigDecimal AMOUNT_200 = new BigDecimal("200.00");

    private static final BankAccountId SENDER_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final BankAccountId RECIPIENT_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final Iban RECIPIENT_IBAN = Iban.of(TestIbanExamples.VALID_ITALY);

    private static final InternalAccountSender INTERNAL_ACCOUNT_SENDER = InternalAccountSender.of(SENDER_ACCOUNT_ID);
    private static final InternalIbanRecipient INTERNAL_IBAN_RECIPIENT = InternalIbanRecipient.of(RECIPIENT_IBAN);

    private static final String TRANSFER_REFERENCE = "transfer reference";

    @Mock
    private LookupBankAccountFacade lookupBankAccountFacadeMock;

    @Mock
    private BankTransferRepository bankTransferRepositoryMock;

    @Mock
    private BankAccount accountSenderMock;

    @Mock
    private BankAccount accountRecipientMock;

    @Captor
    private ArgumentCaptor<BankTransfer> bankTransferCaptor;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Clock clock = TestClockExamples.FIXED_CLOCK_UTC;

    private ProcessInternalTransferUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ProcessInternalTransferUseCaseImpl(lookupBankAccountFacadeMock, bankTransferRepositoryMock, executorService, clock);
    }

    @AfterEach
    void tearDown() {
        executorService.shutdownNow();
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldCreateInternalBankTransfer_whenCommandIsValid(Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);
        var command = CreateInternalTransferFromAccountIdToIbanCommand.of(
            INTERNAL_ACCOUNT_SENDER,
            INTERNAL_IBAN_RECIPIENT,
            monetaryAmount,
            TRANSFER_REFERENCE,
            ZonedDateTime.now(clock)
        );

        when(accountSenderMock.bankAccountId()).thenReturn(SENDER_ACCOUNT_ID);
        when(accountRecipientMock.bankAccountId()).thenReturn(RECIPIENT_ACCOUNT_ID);

        when(lookupBankAccountFacadeMock.resolveAsync(command.partySender().identifier(), executorService))
            .thenReturn(CompletableFuture.completedFuture(accountSenderMock));
        when(lookupBankAccountFacadeMock.resolveAsync(command.partyRecipient().identifier(), executorService))
            .thenReturn(CompletableFuture.completedFuture(accountRecipientMock));

        useCase.execute(command);

        verify(lookupBankAccountFacadeMock).resolveAsync(any(InternalAccountIdIdentifier.class), any());
        verify(lookupBankAccountFacadeMock).resolveAsync(any(InternalAccountIbanIdentifier.class), any());

        verify(bankTransferRepositoryMock).save(bankTransferCaptor.capture());
        assertThat(bankTransferCaptor.getValue())
            .satisfies(bankTransfer -> {
                assertThat(bankTransfer.bankTransferId()).isNotNull();
                assertThat(bankTransfer.transferType().isInternal()).isTrue();
                assertThat(bankTransfer.transferEntries())
                    .hasSize(2)
                    .containsExactlyInAnyOrder(
                        InternalTransferEntry.ofDebit(SENDER_ACCOUNT_ID, RECIPIENT_ACCOUNT_ID, monetaryAmount),
                        InternalTransferEntry.ofCredit(SENDER_ACCOUNT_ID, RECIPIENT_ACCOUNT_ID, monetaryAmount)
                    );
                assertThat(bankTransfer.createdAt().toInstant()).isEqualTo(clock.instant());
                assertThat(bankTransfer.createdAt().getZone()).isEqualTo(clock.getZone());
                assertThat(bankTransfer.reference()).isEqualTo(TRANSFER_REFERENCE);
            });
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldThrowBankAccountNotFoundException_whenInternalAccountSenderIsNotFound(Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);
        var command = CreateInternalTransferFromAccountIdToIbanCommand.of(
            INTERNAL_ACCOUNT_SENDER,
            INTERNAL_IBAN_RECIPIENT,
            monetaryAmount,
            TRANSFER_REFERENCE,
            ZonedDateTime.now(clock)
        );

        var bankAccountNotFoundException = new BankAccountNotFoundException(INTERNAL_ACCOUNT_SENDER.bankAccountId());

        when(lookupBankAccountFacadeMock.resolveAsync(command.partySender().identifier(), executorService))
            .thenReturn(CompletableFuture.failedFuture(bankAccountNotFoundException));

        when(lookupBankAccountFacadeMock.resolveAsync(command.partyRecipient().identifier(), executorService))
            .thenReturn(CompletableFuture.completedFuture(accountRecipientMock));

        assertThatThrownBy(() -> useCase.execute(command))
            .isInstanceOf(CompletionException.class)
            .hasCauseInstanceOf(BankAccountNotFoundException.class)
            .extracting(Throwable::getCause)
            .satisfies(cause -> assertThat(cause.getMessage()).isEqualTo(BankAccountNotFoundException.BANK_ACCOUNT_BY_ACCOUNT_ID_NOT_FOUND_TEMPLATE));

        verifyNoInteractions(bankTransferRepositoryMock);
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldThrowException_whenRecipientBankAccountNotFound(Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);
        var command = CreateInternalTransferFromAccountIdToIbanCommand.of(
            INTERNAL_ACCOUNT_SENDER,
            INTERNAL_IBAN_RECIPIENT,
            monetaryAmount,
            TRANSFER_REFERENCE,
            ZonedDateTime.now(clock)
        );

        when(lookupBankAccountFacadeMock.resolveAsync(command.partySender().identifier(), executorService))
            .thenReturn(CompletableFuture.completedFuture(accountSenderMock));

        var bankAccountNotFoundException = new BankAccountNotFoundException(INTERNAL_IBAN_RECIPIENT.iban());
        when(lookupBankAccountFacadeMock.resolveAsync(command.partyRecipient().identifier(), executorService))
            .thenReturn(CompletableFuture.failedFuture(bankAccountNotFoundException));

        assertThatThrownBy(() -> useCase.execute(command))
            .isInstanceOf(CompletionException.class)
            .hasCauseInstanceOf(BankAccountNotFoundException.class)
            .extracting(Throwable::getCause)
            .satisfies(cause -> assertThat(cause.getMessage()).isEqualTo(BankAccountNotFoundException.BANK_ACCOUNT_BY_IBAN_NOT_FOUND_TEMPLATE));

        verifyNoInteractions(bankTransferRepositoryMock);
    }
}