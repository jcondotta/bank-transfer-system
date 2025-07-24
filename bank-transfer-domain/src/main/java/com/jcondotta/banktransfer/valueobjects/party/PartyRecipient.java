package com.jcondotta.banktransfer.valueobjects.party;

public sealed interface PartyRecipient
    extends Party
    permits InternalPartyRecipient, ExternalPartyRecipient {

    String RECIPIENT_IDENTIFIER_NOT_NULL_MESSAGE = "recipient's identifier must not be null.";
}