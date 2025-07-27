package com.jcondotta.transfer.domain.banktransfer.valueobjects.party;

public sealed interface InternalPartyRecipient
    extends PartyRecipient, InternalParty
    permits InternalAccountRecipient, InternalIbanRecipient {
}