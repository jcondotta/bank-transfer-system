package com.jcondotta.banktransfer.valueobjects;

public sealed interface InternalPartySender
    extends PartySender, InternalParty
    permits InternalAccountSender, InternalIbanSender {

}