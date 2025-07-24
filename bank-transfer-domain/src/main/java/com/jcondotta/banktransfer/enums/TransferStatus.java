package com.jcondotta.banktransfer.enums;

public enum TransferStatus {
    PENDING,
    COMPLETED,
    FAILED;

    public boolean isPending() {
        return this == PENDING;
    }

    public boolean isCompleted() {
        return this == COMPLETED;
    }

    public boolean isFailed() {
        return this == FAILED;
    }
}
