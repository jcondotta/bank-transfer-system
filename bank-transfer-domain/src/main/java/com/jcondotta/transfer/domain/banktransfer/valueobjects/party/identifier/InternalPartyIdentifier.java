package com.jcondotta.transfer.domain.banktransfer.valueobjects.party.identifier;

public sealed interface InternalPartyIdentifier permits InternalAccountIdIdentifier, InternalAccountIbanIdentifier {
    InternalPartyIdentifierType type();
    String value();

    default String asLogString() {
        return "%s=%s".formatted(type().displayName(), value());
    }
}