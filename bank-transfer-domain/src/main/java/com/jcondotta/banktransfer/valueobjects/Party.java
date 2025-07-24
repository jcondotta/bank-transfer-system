package com.jcondotta.banktransfer.valueobjects;

public sealed interface Party permits PartySender, PartyRecipient {
}
