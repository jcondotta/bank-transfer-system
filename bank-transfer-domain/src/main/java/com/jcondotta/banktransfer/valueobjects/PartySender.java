package com.jcondotta.banktransfer.valueobjects;

public sealed interface PartySender
    extends Party
    permits InternalPartySender, ExternalPartySender {

    String SENDER_IDENTIFIER_NOT_NULL_MESSAGE = "sender's identifier must not be null.";
}