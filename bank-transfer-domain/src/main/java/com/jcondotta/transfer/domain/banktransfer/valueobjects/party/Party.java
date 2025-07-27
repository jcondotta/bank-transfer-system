package com.jcondotta.transfer.domain.banktransfer.valueobjects.party;

public sealed interface Party permits PartySender, PartyRecipient {
}
