package com.jcondotta.banktransfer.valueobjects.party.identifier;

public sealed interface InternalPartyIdentifier permits InternalAccountIdIdentifier, InternalAccountIbanIdentifier {
    InternalPartyIdentifierType type();
    String value();
}