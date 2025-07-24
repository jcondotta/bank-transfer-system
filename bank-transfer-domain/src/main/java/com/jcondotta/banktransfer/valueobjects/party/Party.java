package com.jcondotta.banktransfer.valueobjects.party;

public sealed interface Party permits PartySender, PartyRecipient {
}
