package com.jcondotta.transfer.domain.banktransfer.entity;

import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.banktransfer.entity.BankTransfer;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.PartyRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.PartySender;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.transfer_entry.InternalTransferEntry;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryMovement;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import com.jcondotta.test_support.clock.TestClockExamples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BankTransferTest {

    private static final BankAccountId SENDER_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final BankAccountId RECIPIENT_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());

    private static final BigDecimal AMOUNT_200 = new BigDecimal("200.00");

    private static final String TRANSFER_REFERENCE = "Internal transfer for testing";
    private static final Clock REQUESTED_AT = TestClockExamples.FIXED_CLOCK_MADRID;

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldInitiateInternalTransfer_whenAllParamsAreValid(Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);
        var bankTransfer = BankTransfer.initiateInternalTransfer(
            SENDER_ACCOUNT_ID,
            RECIPIENT_ACCOUNT_ID,
            monetaryAmount,
            TRANSFER_REFERENCE,
            REQUESTED_AT
        );

        assertThat(bankTransfer.bankTransferId()).isNotNull();
        assertThat(bankTransfer.transferType().isInternal()).isTrue();
        assertThat(bankTransfer.transferEntries())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                InternalTransferEntry.ofDebit(SENDER_ACCOUNT_ID, RECIPIENT_ACCOUNT_ID, monetaryAmount),
                InternalTransferEntry.ofCredit(SENDER_ACCOUNT_ID, RECIPIENT_ACCOUNT_ID, monetaryAmount)
            );
        assertThat(bankTransfer.reference()).isEqualTo(TRANSFER_REFERENCE);
        assertThat(bankTransfer.createdAt().toInstant()).isEqualTo(REQUESTED_AT.instant());
        assertThat(bankTransfer.createdAt().getZone()).isEqualTo(REQUESTED_AT.getZone());
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldThrowNullPointerException_whenInternalTransferSenderAccountIdIsNull(Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);

        assertThatThrownBy(() -> BankTransfer.initiateInternalTransfer(null, RECIPIENT_ACCOUNT_ID, monetaryAmount, TRANSFER_REFERENCE, REQUESTED_AT))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(PartySender.SENDER_IDENTIFIER_NOT_NULL_MESSAGE);
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldThrowNullPointerException_whenInternalTransferRecipientAccountIdIsNull(Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);

        assertThatThrownBy(() -> BankTransfer.initiateInternalTransfer(SENDER_ACCOUNT_ID, null, monetaryAmount, TRANSFER_REFERENCE, REQUESTED_AT))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(PartyRecipient.RECIPIENT_IDENTIFIER_NOT_NULL_MESSAGE);
    }

    @Test
    void shouldThrowNullPointerException_whenInternalTransferAmountIsNull() {
        assertThatThrownBy(() -> BankTransfer.initiateInternalTransfer(SENDER_ACCOUNT_ID, RECIPIENT_ACCOUNT_ID, null, TRANSFER_REFERENCE, REQUESTED_AT))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(MonetaryMovement.MONETARY_AMOUNT_NOT_NULL_MESSAGE);
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldThrowNullPointerException_whenInternalTransferClockIsNull(Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);

        assertThatThrownBy(() -> BankTransfer.initiateInternalTransfer(SENDER_ACCOUNT_ID, RECIPIENT_ACCOUNT_ID, monetaryAmount, TRANSFER_REFERENCE, null))
            .isInstanceOf(NullPointerException.class);

    }
}
