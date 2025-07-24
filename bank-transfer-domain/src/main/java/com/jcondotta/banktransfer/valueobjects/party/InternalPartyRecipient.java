package com.jcondotta.banktransfer.valueobjects.party;

public sealed interface InternalPartyRecipient
    extends PartyRecipient, InternalParty
    permits InternalAccountRecipient, InternalIbanRecipient {
}