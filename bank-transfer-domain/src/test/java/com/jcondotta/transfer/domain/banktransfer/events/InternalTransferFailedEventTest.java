package com.jcondotta.transfer.domain.banktransfer.events;

import com.jcondotta.test_support.clock.TestClockExamples;
import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.banktransfer.entity.BankTransfer;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InternalTransferFailedEventTest {

    private static final Clock TRANSFER_FAILED_AT_CLOCK = TestClockExamples.FIXED_CLOCK_UTC;
    private static final ZonedDateTime TRANSFER_FAILED_AT = TestClockExamples.FIXED_DATE_TIME_UTC;

    private static final String FAILURE_IDENTICAL_PARTIES = "internal.transfer.identicalParties";
    private static final String FAILURE_INSUFFICIENT_FUNDS = "internal.transfer.insufficientFunds";

    @Test
    void shouldCreateFailedEvent_whenBankTransferHasSingleFailure() {
        assertThat(InternalTransferFailedEvent.of(FAILURE_IDENTICAL_PARTIES, TRANSFER_FAILED_AT_CLOCK))
            .satisfies(failedEvent -> {
                assertThat(failedEvent.failures())
                    .hasSize(1)
                    .containsExactly(FAILURE_IDENTICAL_PARTIES);

                assertThat(failedEvent.failedAt().toInstant()).isEqualTo(TRANSFER_FAILED_AT.toInstant());
                assertThat(failedEvent.failedAt().getZone()).isEqualTo(TRANSFER_FAILED_AT.getZone());
            });
    }

    @Test
    void shouldCreateFailedEvent_whenBankTransferHasMultipleFailures() {
        var failureReasons = List.of(FAILURE_IDENTICAL_PARTIES, FAILURE_INSUFFICIENT_FUNDS);

        assertThat(InternalTransferFailedEvent.of(failureReasons, TRANSFER_FAILED_AT_CLOCK))
            .satisfies(failedEvent -> {
                assertThat(failedEvent.failures())
                    .hasSize(2)
                    .containsExactlyElementsOf(failureReasons);

                assertThat(failedEvent.failedAt().toInstant()).isEqualTo(TRANSFER_FAILED_AT.toInstant());
                assertThat(failedEvent.failedAt().getZone()).isEqualTo(TRANSFER_FAILED_AT.getZone());
            });
    }
}
