package com.jcondotta.transfer.domain.banktransfer.events;

import com.jcondotta.transfer.domain.banktransfer.entity.BankTransfer;

import java.time.Clock;
import java.time.ZonedDateTime;

public record InternalTransferCompletedEvent(BankTransfer bankTransfer, ZonedDateTime completedAt) {

    public static InternalTransferCompletedEvent of(BankTransfer bankTransfer, Clock completedAt) {
        return new InternalTransferCompletedEvent(bankTransfer, ZonedDateTime.now(completedAt));
    }
}
