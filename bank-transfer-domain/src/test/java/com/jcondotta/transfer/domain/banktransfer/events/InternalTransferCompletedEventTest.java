package com.jcondotta.transfer.domain.banktransfer.events;

import com.jcondotta.test_support.clock.TestClockExamples;
import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.banktransfer.entity.BankTransfer;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.transfer_entry.InternalTransferEntry;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InternalTransferCompletedEventTest {

    private static final BankAccountId SENDER_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final BankAccountId RECIPIENT_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());

    private static final BigDecimal AMOUNT_200 = new BigDecimal("200.00");

    private static final String TRANSFER_REFERENCE = "Internal transfer for testing";
    private static final Clock TRANSFER_INITIATED_AT = TestClockExamples.FIXED_CLOCK_UTC;
    private static final ZonedDateTime TRANSFER_COMPLETED_AT = TestClockExamples.FIXED_DATE_TIME_UTC;

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldCreateCompletedEvent_whenBankTransferIsValid(Currency currency) {
        var bankTransfer = BankTransfer.initiateInternalTransfer(
            SENDER_ACCOUNT_ID,
            RECIPIENT_ACCOUNT_ID,
            MonetaryAmount.of(AMOUNT_200, currency),
            TRANSFER_REFERENCE,
            TRANSFER_INITIATED_AT
        );

        assertThat(InternalTransferCompletedEvent.of(bankTransfer, TRANSFER_INITIATED_AT))
            .satisfies(completedEvent -> {
                assertThat(completedEvent.bankTransfer())
                    .usingRecursiveAssertion()
                    .isEqualTo(bankTransfer);

                assertThat(completedEvent.completedAt().toInstant()).isEqualTo(TRANSFER_COMPLETED_AT.toInstant());
                assertThat(completedEvent.completedAt().getZone()).isEqualTo(TRANSFER_COMPLETED_AT.getZone());
            });
    }
}
