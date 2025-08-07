package com.jcondotta.transfer.domain.banktransfer.valueobjects.party.identifier;

public enum InternalPartyIdentifierType {
    ACCOUNT_ID("accountId"),
    IBAN("iban");

    private final String display;

    InternalPartyIdentifierType(String display) {
        this.display = display;
    }

    public String displayName() {
        return display;
    }

    public static InternalPartyIdentifierType from(String value) {
        try {
            return InternalPartyIdentifierType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown InternalPartyIdentifierType: " + value, e);
        }
    }
}
