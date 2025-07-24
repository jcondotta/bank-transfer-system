package com.jcondotta.banktransfer.valueobjects.party;

public sealed interface InternalPartySender
    extends PartySender, InternalParty
    permits InternalAccountSender {

}