package com.jcondotta.transfer.domain.banktransfer.valueobjects.party;

public sealed interface InternalPartySender
    extends PartySender, InternalParty
    permits InternalAccountSender {

}