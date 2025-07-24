package com.jcondotta.bank_account.enums;

public enum AccountStatus {
    ACTIVE, CANCELLED;

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean isCancelled() {
        return this == CANCELLED;
    }
}