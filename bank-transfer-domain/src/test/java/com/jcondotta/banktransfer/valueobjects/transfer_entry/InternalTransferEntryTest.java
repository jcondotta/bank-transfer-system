package com.jcondotta.banktransfer.valueobjects.transfer_entry;

import com.jcondotta.argumentprovider.MovementTypeAndCurrencyArgumentsProvider;
import com.jcondotta.bank_account.valueobject.BankAccountId;
import com.jcondotta.banktransfer.valueobjects.party.InternalAccountRecipient;
import com.jcondotta.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.monetary_movement.enums.MovementType;
import com.jcondotta.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.monetary_movement.value_objects.MonetaryMovement;
import com.jcondotta.shared.valueobjects.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InternalTransferEntryTest {

    private static final BankAccountId SENDER_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final BankAccountId RECIPIENT_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());

    private static final BigDecimal AMOUNT_200 = new BigDecimal("200.00");

    @ParameterizedTest
    @ArgumentsSource(MovementTypeAndCurrencyArgumentsProvider.class)
    void shouldCreateInternalTransferEntry_whenParamsAreValid(MovementType movementType, Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);
        var monetaryMovement = MonetaryMovement.of(movementType, monetaryAmount);

        var internalPartySender = InternalAccountSender.of(SENDER_ACCOUNT_ID);
        var internalPartyRecipient = InternalAccountRecipient.of(RECIPIENT_ACCOUNT_ID);

        var transferEntry = new InternalTransferEntry(internalPartySender, internalPartyRecipient, monetaryMovement);

        assertThat(transferEntry.partySender().bankAccountId()).isEqualTo(SENDER_ACCOUNT_ID);
        assertThat(transferEntry.partyRecipient().bankAccountId()).isEqualTo(RECIPIENT_ACCOUNT_ID);
        assertThat(transferEntry.monetaryMovement()).isEqualTo(monetaryMovement);
        assertThat(transferEntry.amount()).isEqualTo(AMOUNT_200);
        assertThat(transferEntry.currency()).isEqualTo(currency);
        assertThat(transferEntry.movementType()).isEqualTo(movementType);
        assertThat(transferEntry.isDebit()).isEqualTo(movementType.isDebit());
        assertThat(transferEntry.isCredit()).isEqualTo(movementType.isCredit());
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldCreateInternalTransferEntry_whenUsingOfFactoryMethodWithDebit(Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);

        var transferEntry = InternalTransferEntry.ofDebit(SENDER_ACCOUNT_ID, RECIPIENT_ACCOUNT_ID, monetaryAmount);

        assertThat(transferEntry.partySender().bankAccountId()).isEqualTo(SENDER_ACCOUNT_ID);
        assertThat(transferEntry.partyRecipient().bankAccountId()).isEqualTo(RECIPIENT_ACCOUNT_ID);
        assertThat(transferEntry.monetaryMovement().movementType()).isEqualTo(MovementType.DEBIT);
        assertThat(transferEntry.monetaryMovement().monetaryAmount()).isEqualTo(monetaryAmount);
        assertThat(transferEntry.amount()).isEqualTo(AMOUNT_200);
        assertThat(transferEntry.currency()).isEqualTo(currency);
        assertThat(transferEntry.movementType()).isEqualTo(MovementType.DEBIT);
        assertThat(transferEntry.isDebit()).isTrue();
        assertThat(transferEntry.isCredit()).isFalse();
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldCreateInternalTransferEntry_whenUsingOfFactoryMethodWithCredit(Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);

        var transferEntry = InternalTransferEntry.ofCredit(SENDER_ACCOUNT_ID, RECIPIENT_ACCOUNT_ID, monetaryAmount);

        assertThat(transferEntry.partySender().bankAccountId()).isEqualTo(SENDER_ACCOUNT_ID);
        assertThat(transferEntry.partyRecipient().bankAccountId()).isEqualTo(RECIPIENT_ACCOUNT_ID);
        assertThat(transferEntry.monetaryMovement().movementType()).isEqualTo(MovementType.CREDIT);
        assertThat(transferEntry.amount()).isEqualTo(AMOUNT_200);
        assertThat(transferEntry.currency()).isEqualTo(currency);
        assertThat(transferEntry.movementType()).isEqualTo(MovementType.CREDIT);
        assertThat(transferEntry.isCredit()).isTrue();
        assertThat(transferEntry.isDebit()).isFalse();
    }

    @ParameterizedTest
    @ArgumentsSource(MovementTypeAndCurrencyArgumentsProvider.class)
    void shouldThrowNullPointerException_whenSenderIdentifierIsNull(MovementType movementType, Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);
        var monetaryMovement = MonetaryMovement.of(movementType, monetaryAmount);

        var internalPartyRecipient = InternalAccountRecipient.of(RECIPIENT_ACCOUNT_ID);

        assertThatThrownBy(() -> new InternalTransferEntry(null, internalPartyRecipient, monetaryMovement))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(TransferEntry.SENDER_IDENTIFIER_NOT_NULL_MESSAGE);
    }

    @ParameterizedTest
    @ArgumentsSource(MovementTypeAndCurrencyArgumentsProvider.class)
    void shouldThrowNullPointerException_whenRecipientIdentifierIsNull(MovementType movementType, Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);
        var monetaryMovement = MonetaryMovement.of(movementType, monetaryAmount);

        var internalPartySender = InternalAccountSender.of(SENDER_ACCOUNT_ID);

        assertThatThrownBy(() -> new InternalTransferEntry(internalPartySender, null, monetaryMovement))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(TransferEntry.RECIPIENT_IDENTIFIER_NOT_NULL_MESSAGE);
    }

    @Test
    void shouldThrowNullPointerException_whenMonetaryMovementIsNull() {
        var internalPartySender = InternalAccountSender.of(SENDER_ACCOUNT_ID);
        var internalPartyRecipient = InternalAccountRecipient.of(RECIPIENT_ACCOUNT_ID);

        assertThatThrownBy(() -> new InternalTransferEntry(internalPartySender, internalPartyRecipient, null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(TransferEntry.MONETARY_MOVEMENT_NOT_NULL_MESSAGE);
    }

    @ParameterizedTest
    @ArgumentsSource(MovementTypeAndCurrencyArgumentsProvider.class)
    void shouldThrowNullPointerException_whenUsingOfFactoryMethodWithSenderAccountIdIsNull(MovementType movementType, Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);
        assertThatThrownBy(() -> InternalTransferEntry.of(null, RECIPIENT_ACCOUNT_ID, movementType, monetaryAmount))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(TransferEntry.SENDER_IDENTIFIER_NOT_NULL_MESSAGE);
    }

    @ParameterizedTest
    @ArgumentsSource(MovementTypeAndCurrencyArgumentsProvider.class)
    void shouldThrowNullPointerException_whenUsingOfFactoryMethodWithRecipientAccountIdIsNull(MovementType movementType, Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);
        assertThatThrownBy(() -> InternalTransferEntry.of(SENDER_ACCOUNT_ID, null, movementType, monetaryAmount))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(TransferEntry.RECIPIENT_IDENTIFIER_NOT_NULL_MESSAGE);
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldThrowNullPointerException_whenUsingOfFactoryMethodWithMovementTypeIsNull(Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);
        assertThatThrownBy(() -> InternalTransferEntry.of(SENDER_ACCOUNT_ID, RECIPIENT_ACCOUNT_ID, null, monetaryAmount))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(MonetaryMovement.MOVEMENT_TYPE_NOT_NULL_MESSAGE);
    }

    @ParameterizedTest
    @EnumSource(MovementType.class)
    void shouldThrowNullPointerException_whenUsingOfFactoryMethodWithMonetaryAmountIsNull(MovementType movementType) {
        assertThatThrownBy(() -> InternalTransferEntry.of(SENDER_ACCOUNT_ID, RECIPIENT_ACCOUNT_ID, movementType, null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(MonetaryMovement.MONETARY_AMOUNT_NOT_NULL_MESSAGE);
    }
}