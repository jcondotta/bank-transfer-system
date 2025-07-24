package com.jcondotta.banktransfer.valueobjects;

public sealed interface InternalPartyRecipient
    extends PartyRecipient, InternalParty
    permits InternalAccountRecipient, InternalIbanRecipient {
}