package com.jcondotta.transfer.domain.banktransfer.enums;

import com.jcondotta.transfer.domain.banktransfer.enums.TransferStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TransferStatusTest {

    @Test
    void shouldAssertInfoCorrectly_whenTransferStatusIsCompleted() {
        assertThat(TransferStatus.COMPLETED)
            .satisfies(transferStatus -> {
                assertThat(transferStatus.isCompleted()).isTrue();
                assertThat(transferStatus.isPending()).isFalse();
                assertThat(transferStatus.isFailed()).isFalse();
            });
    }

    @Test
    void shouldAssertInfoCorrectly_whenTransferStatusIsPending() {
        assertThat(TransferStatus.PENDING)
            .satisfies(transferStatus -> {
                assertThat(transferStatus.isPending()).isTrue();
                assertThat(transferStatus.isCompleted()).isFalse();
                assertThat(transferStatus.isFailed()).isFalse();
            });
    }

    @Test
    void shouldAssertInfoCorrectly_whenTransferStatusIsFailed() {
        assertThat(TransferStatus.FAILED)
            .satisfies(transferStatus -> {
                assertThat(transferStatus.isFailed()).isTrue();
                assertThat(transferStatus.isCompleted()).isFalse();
                assertThat(transferStatus.isPending()).isFalse();
            });
    }

}