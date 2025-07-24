package com.jcondotta.bank_account.entity;

import com.jcondotta.bank_account.enums.AccountStatus;
import com.jcondotta.bank_account.enums.AccountType;
import com.jcondotta.bank_account.valueobject.BankAccountId;
import com.jcondotta.bank_account.valueobject.Iban;
import com.jcondotta.shared.valueobjects.Currency;

import java.time.ZonedDateTime;

import static java.util.Objects.requireNonNull;

public record BankAccount(BankAccountId bankAccountId, AccountType accountType, Currency currency, AccountStatus status, Iban iban, ZonedDateTime createdAt) {

    static final String BANK_ACCOUNT_ID_NOT_NULL = "bankAccountId must not be null.";
    static final String ACCOUNT_TYPE_NOT_NULL = "accountType must not be null.";
    static final String CURRENCY_NOT_NULL = "currency must not be null.";
    static final String STATUS_NOT_NULL = "status must not be null.";
    static final String IBAN_NOT_NULL = "iban must not be null.";
    static final String CREATED_AT_NOT_NULL = "createdAt must not be null.";

    public BankAccount {
        requireNonNull(bankAccountId, BANK_ACCOUNT_ID_NOT_NULL);
        requireNonNull(accountType, ACCOUNT_TYPE_NOT_NULL);
        requireNonNull(currency, CURRENCY_NOT_NULL);
        requireNonNull(status, STATUS_NOT_NULL);
        requireNonNull(iban, IBAN_NOT_NULL);
        requireNonNull(createdAt, CREATED_AT_NOT_NULL);
    }

    public static BankAccount of(BankAccountId bankAccountId, AccountType accountType, Currency currency, AccountStatus status, Iban iban, ZonedDateTime createdAt) {
        return new BankAccount(bankAccountId, accountType, currency, status, iban, createdAt);
    }

    public boolean isCheckingAccount() {
        return accountType == AccountType.CHECKING;
    }

    public boolean isSavingsAccount() {
        return accountType == AccountType.SAVINGS;
    }

    public boolean isActive() {
        return status == AccountStatus.ACTIVE;
    }
}