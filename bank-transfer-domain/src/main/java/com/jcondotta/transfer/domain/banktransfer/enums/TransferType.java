package com.jcondotta.transfer.domain.banktransfer.enums;

public enum TransferType {
    INTERNAL,
    INCOMING_EXTERNAL,
    OUTGOING_EXTERNAL;

    public boolean isInternal() {
        return this == INTERNAL;
    }

    public boolean isIncomingExternal() {
        return this == INCOMING_EXTERNAL;
    }

    public boolean isOutgoingExternal() {
        return this == OUTGOING_EXTERNAL;
    }
}