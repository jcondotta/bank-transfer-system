package com.jcondotta.bank_account.entity;

import com.jcondotta.argumentprovider.AccountTypeAndCurrencyArgumentsProvider;
import com.jcondotta.bank_account.enums.AccountStatus;
import com.jcondotta.bank_account.enums.AccountType;
import com.jcondotta.bank_account.valueobject.BankAccountId;
import com.jcondotta.bank_account.valueobject.Iban;
import com.jcondotta.shared.valueobjects.Currency;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.ZonedDateTime;
import java.util.UUID;

import static com.jcondotta.bank_account.entity.BankAccount.*;
import static java.time.ZoneOffset.UTC;
import static java.time.ZonedDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BankAccountTest {

    private static final BankAccountId BANK_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final AccountStatus ACTIVE_ACCOUNT_STATUS = AccountStatus.ACTIVE;
    private static final Iban VALID_IBAN = Iban.of("ES3801283316232166447417");
    private static final ZonedDateTime VALID_DATE = now(UTC);

    @ParameterizedTest
    @ArgumentsSource(AccountTypeAndCurrencyArgumentsProvider.class)
    void shouldCreateBankAccount_whenAllFieldsAreValid(AccountType accountType, Currency currency) {
        var bankAccount = BankAccount.of(BANK_ACCOUNT_ID, accountType, currency, ACTIVE_ACCOUNT_STATUS, VALID_IBAN, VALID_DATE);

        assertThat(bankAccount.bankAccountId()).isEqualTo(BANK_ACCOUNT_ID);
        assertThat(bankAccount.accountType()).isEqualTo(accountType);
        assertThat(bankAccount.currency()).isEqualTo(currency);
        assertThat(bankAccount.status()).isEqualTo(ACTIVE_ACCOUNT_STATUS);
        assertThat(bankAccount.iban()).isEqualTo(VALID_IBAN);
        assertThat(bankAccount.createdAt()).isEqualTo(VALID_DATE);
    }

    @ParameterizedTest
    @ArgumentsSource(AccountTypeAndCurrencyArgumentsProvider.class)
    void shouldThrowNullPointerException_whenBankAccountIdIsNull(AccountType accountType, Currency currency) {
        assertThatThrownBy(() -> BankAccount.of(null, accountType, currency, ACTIVE_ACCOUNT_STATUS, VALID_IBAN, VALID_DATE))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(BANK_ACCOUNT_ID_NOT_NULL);
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldThrowNullPointerException_whenAccountTypeIsNull(Currency currency) {
        assertThatThrownBy(() -> BankAccount.of(BANK_ACCOUNT_ID, null, currency, ACTIVE_ACCOUNT_STATUS, VALID_IBAN, VALID_DATE))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(ACCOUNT_TYPE_NOT_NULL);
    }

    @ParameterizedTest
    @EnumSource(AccountType.class)
    void shouldThrowNullPointerException_whenCurrencyIsNull(AccountType accountType) {
        assertThatThrownBy(() -> BankAccount.of(BANK_ACCOUNT_ID, accountType, null, ACTIVE_ACCOUNT_STATUS, VALID_IBAN, VALID_DATE))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(CURRENCY_NOT_NULL);
    }

    @ParameterizedTest
    @ArgumentsSource(AccountTypeAndCurrencyArgumentsProvider.class)
    void shouldThrowNullPointerException_whenStatusIsNull(AccountType accountType, Currency currency) {
        assertThatThrownBy(() -> BankAccount.of(BANK_ACCOUNT_ID, accountType, currency, null, VALID_IBAN, VALID_DATE))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(STATUS_NOT_NULL);
    }

    @ParameterizedTest
    @ArgumentsSource(AccountTypeAndCurrencyArgumentsProvider.class)
    void shouldThrowNullPointerException_whenIbanIsNull(AccountType accountType, Currency currency) {
        assertThatThrownBy(() -> BankAccount.of(BANK_ACCOUNT_ID, accountType, currency, ACTIVE_ACCOUNT_STATUS, null, VALID_DATE))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(IBAN_NOT_NULL);
    }

    @ParameterizedTest
    @ArgumentsSource(AccountTypeAndCurrencyArgumentsProvider.class)
    void shouldThrowNullPointerException_whenCreatedAtIsNull(AccountType accountType, Currency currency) {
        assertThatThrownBy(() -> BankAccount.of(BANK_ACCOUNT_ID, accountType, currency, ACTIVE_ACCOUNT_STATUS, VALID_IBAN, null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(CREATED_AT_NOT_NULL);
    }

    @ParameterizedTest
    @EnumSource(AccountType.class)
    void shouldReturnCorrectAccountTypeCheckFlags_whenAccountTypeIsProvided(AccountType accountType) {
        var bankAccount = BankAccount.of(BANK_ACCOUNT_ID, accountType, Currency.EUR, ACTIVE_ACCOUNT_STATUS, VALID_IBAN, VALID_DATE);

        assertThat(bankAccount.isCheckingAccount()).isEqualTo(accountType.isChecking());
        assertThat(bankAccount.isSavingsAccount()).isEqualTo(accountType.isSavings());
    }

    @ParameterizedTest
    @EnumSource(AccountStatus.class)
    void shouldReturnCorrectStatusFlag_whenStatusIsProvided(AccountStatus status) {
        var account = BankAccount.of(BANK_ACCOUNT_ID, AccountType.CHECKING, Currency.EUR, status, VALID_IBAN, VALID_DATE);

        assertThat(account.isActive()).isEqualTo(status.isActive());
    }
}
