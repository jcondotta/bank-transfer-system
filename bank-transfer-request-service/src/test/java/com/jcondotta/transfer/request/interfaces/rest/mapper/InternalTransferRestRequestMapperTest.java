package com.jcondotta.transfer.request.interfaces.rest.mapper;

import com.jcondotta.transfer.application.usecase.request_internal_transfer.model.RequestInternalTransferFromAccountIdToAccountIdCommand;
import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;
import com.jcondotta.transfer.request.interfaces.rest.model.InternalTransferFromAccountIdToAccountIdRestRequest;
import com.jcondotta.transfer.request.interfaces.rest.model.InternalTransferFromAccountIdToIbanRestRequest;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import com.jcondotta.test_support.iban.TestIbanExamples;
import com.jcondotta.transfer.application.usecase.request_internal_transfer.model.RequestInternalTransferFromAccountIdToIbanCommand;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InternalTransferRestRequestMapperTest {

    private static final BankAccountId SENDER_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final BankAccountId RECIPIENT_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final Iban RECIPIENT_IBAN = Iban.of(TestIbanExamples.VALID_SPAIN);

    private static final BigDecimal AMOUNT_200 = new BigDecimal("200.00");

    private static final String TRANSFER_REFERENCE = "Invoice #437263";

    private final InternalTransferRestRequestMapper mapper = new InternalTransferRestRequestMapperImpl();

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldMapRestRequestToCommand_InternalTransferFromAccountIdToIban(Currency currency) {
        var request = new InternalTransferFromAccountIdToIbanRestRequest(
            SENDER_ACCOUNT_ID.value(),
            RECIPIENT_IBAN.value(),
            AMOUNT_200,
            currency.name(),
            TRANSFER_REFERENCE
        );

        assertThat(mapper.toCommand(request))
            .isInstanceOf(RequestInternalTransferFromAccountIdToIbanCommand.class)
            .satisfies(command -> {
                var casted = (RequestInternalTransferFromAccountIdToIbanCommand) command;
                assertThat(casted.partySender().bankAccountId()).isEqualTo(SENDER_ACCOUNT_ID);
                assertThat(casted.partyRecipient().iban()).isEqualTo(RECIPIENT_IBAN);
                assertThat(casted.monetaryAmount().amount()).isEqualByComparingTo(AMOUNT_200);
                assertThat(casted.monetaryAmount().currency()).isEqualTo(currency);
                assertThat(casted.reference()).isEqualTo(TRANSFER_REFERENCE);
            });
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldMapRestRequestToCommand_whenRequestIsInternalTransferFromAccountIdToAccountId(Currency currency) {
        var request = new InternalTransferFromAccountIdToAccountIdRestRequest(
            SENDER_ACCOUNT_ID.value(),
            RECIPIENT_ACCOUNT_ID.value(),
            AMOUNT_200,
            currency.name(),
            TRANSFER_REFERENCE
        );

        assertThat(mapper.toCommand(request))
            .isInstanceOf(RequestInternalTransferFromAccountIdToAccountIdCommand.class)
            .satisfies(command -> {
                var casted = (RequestInternalTransferFromAccountIdToAccountIdCommand) command;
                assertThat(casted.partySender().bankAccountId()).isEqualTo(SENDER_ACCOUNT_ID);
                assertThat(casted.partyRecipient().bankAccountId()).isEqualTo(RECIPIENT_ACCOUNT_ID);
                assertThat(casted.monetaryAmount().amount()).isEqualByComparingTo(AMOUNT_200);
                assertThat(casted.monetaryAmount().currency()).isEqualTo(currency);
                assertThat(casted.reference()).isEqualTo(TRANSFER_REFERENCE);
            });
    }
}