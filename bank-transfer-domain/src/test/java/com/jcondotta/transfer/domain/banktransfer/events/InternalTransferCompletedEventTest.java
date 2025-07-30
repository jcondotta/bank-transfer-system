package com.jcondotta.transfer.domain.banktransfer.events;

import com.jcondotta.test_support.clock.TestClockExamples;
import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.banktransfer.entity.BankTransfer;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InternalTransferCompletedEventTest {

    private static final BankAccountId SENDER_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final BankAccountId RECIPIENT_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());

    private static final BigDecimal AMOUNT_200 = new BigDecimal("200.00");

    private static final String TRANSFER_REFERENCE = "Invoice #9747263";

    private static final Clock FIXED_CLOCK_UTC = TestClockExamples.FIXED_CLOCK_UTC;

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldCreateCompletedEvent_whenBankTransferIsValid(Currency currency) {
        var bankTransfer = BankTransfer.initiateInternalTransfer(
            SENDER_ACCOUNT_ID,
            RECIPIENT_ACCOUNT_ID,
            MonetaryAmount.of(AMOUNT_200, currency),
            TRANSFER_REFERENCE,
            FIXED_CLOCK_UTC
        );

        assertThat(InternalTransferCompletedEvent.of(bankTransfer, FIXED_CLOCK_UTC))
            .satisfies(completedEvent -> {
                assertThat(completedEvent.bankTransfer())
                    .usingRecursiveAssertion()
                    .isEqualTo(bankTransfer);

                assertThat(completedEvent.completedAt().toInstant()).isEqualTo(FIXED_CLOCK_UTC.instant());
                assertThat(completedEvent.completedAt().getZone()).isEqualTo(FIXED_CLOCK_UTC.getZone());
            });
    }
}
