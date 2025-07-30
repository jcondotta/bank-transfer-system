package com.jcondotta.transfer.processing.infrastructure.adapters.output.facade.lookup_bank_account;

import com.jcondotta.test_support.iban.TestIbanExamples;
import com.jcondotta.transfer.application.ports.output.banking.LookupBankAccountFacade;
import com.jcondotta.transfer.domain.bank_account.enums.AccountStatus;
import com.jcondotta.transfer.domain.bank_account.enums.AccountType;
import com.jcondotta.transfer.domain.bank_account.exceptions.BankAccountNotFoundException;
import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.client.lookup_bank_account.LookupBankAccountClient;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.client.lookup_bank_account.model.LookupBankAccountResponseCdo;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.facade.lookup_bank_account.mapper.LookupBankAccountCdoFacadeMapper;
import com.jcondotta.transfer.processing.support.arguments.AccountTypeStatusAndCurrencyArgumentsProvider;
import com.jcondotta.transfer.processing.support.instancio.BankAccountCdoModel;
import feign.FeignException;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LookupBankAccountFacadeImplTest {

    @Mock
    private LookupBankAccountClient client;

    private final LookupBankAccountCdoFacadeMapper mapper = LookupBankAccountCdoFacadeMapper.INSTANCE;

    private LookupBankAccountFacade facade;

    private final BankAccountId bankAccountId = BankAccountId.of(UUID.randomUUID());
    private final Iban iban = Iban.of(TestIbanExamples.VALID_ITALY);

    @BeforeEach
    void setUp() {
        facade = new LookupBankAccountFacadeImpl(client, mapper);
    }

    @ParameterizedTest
    @ArgumentsSource(AccountTypeStatusAndCurrencyArgumentsProvider.class)
    void shouldReturnBankAccount_whenAccountIsFoundById(AccountType accountType, AccountStatus accountStatus, Currency currency) {
        var bankAccountCdo = Instancio.of(BankAccountCdoModel.create(accountType, accountStatus, currency, iban)).create();
        var responseCdo = new LookupBankAccountResponseCdo(bankAccountCdo);

        when(client.findById(bankAccountId.value())).thenReturn(responseCdo);

        assertThat(facade.byId(bankAccountId))
            .satisfies(bankAccount -> {
                assertThat(bankAccount.bankAccountId().value()).isEqualTo(bankAccountCdo.bankAccountId());
                assertThat(bankAccount.iban().value()).isEqualTo(bankAccountCdo.iban());
                assertThat(bankAccount.accountType()).hasToString(bankAccountCdo.accountType().name());
                assertThat(bankAccount.currency()).hasToString(bankAccountCdo.currency().name());
                assertThat(bankAccount.status()).hasToString(bankAccountCdo.status().name());
                assertThat(bankAccount.createdAt()).isEqualTo(bankAccountCdo.dateOfOpening());
                assertThat(bankAccount.createdAt()).isEqualTo(bankAccountCdo.dateOfOpening());
            });
    }

    @Test
    void shouldThrowBankAccountNotFoundException_whenAccountIsNotFoundById() {
        when(client.findById(bankAccountId.value()))
            .thenThrow(mock(FeignException.NotFound.class));

        assertThatThrownBy(() -> facade.byId(bankAccountId))
            .isInstanceOf(BankAccountNotFoundException.class)
            .hasMessage("bankAccount.byAccountId.notFound");
    }

    @Test
    void shouldThrowRuntimeException_whenInternalServerErrorById() {
        var exception = mock(FeignException.InternalServerError.class);
        when(client.findById(bankAccountId.value())).thenThrow(exception);

        assertThatThrownBy(() -> facade.byId(bankAccountId))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Internal error on lookup");
    }

    @Test
    void shouldThrowRuntimeException_whenUnexpectedFeignExceptionById() {
        var exception = mock(FeignException.class);
        when(client.findById(bankAccountId.value())).thenThrow(exception);

        assertThatThrownBy(() -> facade.byId(bankAccountId))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Unexpected error");
    }

    @ParameterizedTest
    @ArgumentsSource(AccountTypeStatusAndCurrencyArgumentsProvider.class)
    void shouldReturnBankAccount_whenAccountIsFoundByIban(AccountType accountType, AccountStatus accountStatus, Currency currency) {
        var bankAccountCdo = Instancio.of(BankAccountCdoModel.create(accountType, accountStatus, currency, iban)).create();
        var responseCdo = new LookupBankAccountResponseCdo(bankAccountCdo);

        when(client.findByIban(iban.value())).thenReturn(responseCdo);

        assertThat(facade.byIban(iban))
            .satisfies(bankAccount -> {
                assertThat(bankAccount).isNotNull();
                assertThat(bankAccount.bankAccountId().value()).isEqualTo(bankAccountCdo.bankAccountId());
                assertThat(bankAccount.iban().value()).isEqualTo(bankAccountCdo.iban());
                assertThat(bankAccount.accountType()).hasToString(bankAccountCdo.accountType().name());
                assertThat(bankAccount.currency()).hasToString(bankAccountCdo.currency().name());
                assertThat(bankAccount.status()).hasToString(bankAccountCdo.status().name());
                assertThat(bankAccount.createdAt()).isEqualTo(bankAccountCdo.dateOfOpening());
                assertThat(bankAccount.createdAt()).isEqualTo(bankAccountCdo.dateOfOpening());
            });
    }

    @Test
    void shouldThrowBankAccountNotFoundException_whenNotFoundByIban() {
        when(client.findByIban(iban.value()))
            .thenThrow(mock(FeignException.NotFound.class));

        assertThatThrownBy(() -> facade.byIban(iban))
            .isInstanceOf(BankAccountNotFoundException.class)
            .hasMessage("bankAccount.byIban.notFound");
    }

    @Test
    void shouldThrowRuntimeException_whenInternalServerErrorByIban() {
        var exception = mock(FeignException.InternalServerError.class);
        when(client.findByIban(iban.value())).thenThrow(exception);

        assertThatThrownBy(() -> facade.byIban(iban))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Internal error on lookup");
    }

    @Test
    void shouldThrowRuntimeException_whenUnexpectedFeignExceptionByIban() {
        var exception = mock(FeignException.class);
        when(client.findByIban(iban.value())).thenThrow(exception);

        assertThatThrownBy(() -> facade.byIban(iban))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Unexpected error");
    }
}