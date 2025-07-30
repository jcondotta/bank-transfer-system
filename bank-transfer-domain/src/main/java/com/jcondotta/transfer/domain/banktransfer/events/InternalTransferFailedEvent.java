package com.jcondotta.transfer.domain.banktransfer.events;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;

public record InternalTransferFailedEvent(List<String> failures, ZonedDateTime failedAt) {

    public static InternalTransferFailedEvent of(List<String> failures, Clock failedAt) {
        return new InternalTransferFailedEvent(failures, ZonedDateTime.now(failedAt));
    }

    public static InternalTransferFailedEvent of(String failure, Clock failedAt) {
        return new InternalTransferFailedEvent(List.of(failure), ZonedDateTime.now(failedAt));
    }
}
