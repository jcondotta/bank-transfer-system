package com.jcondotta.transfer.domain.bank_account.enums;

public enum AccountType {
    SAVINGS, CHECKING;

    public boolean isSavings() {
        return this == SAVINGS;
    }

    public boolean isChecking() {
        return this == CHECKING;
    }
}
