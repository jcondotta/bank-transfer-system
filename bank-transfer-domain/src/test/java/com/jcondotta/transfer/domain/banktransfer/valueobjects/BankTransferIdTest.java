package com.jcondotta.transfer.domain.banktransfer.valueobjects;

import com.jcondotta.transfer.domain.banktransfer.valueobjects.BankTransferId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BankTransferIdTest {

    private static final UUID BANK_TRANSFER_UUID_1 = UUID.fromString("1fcaca1b-92ba-43c1-b45c-bacf92868d31");
    private static final UUID BANK_TRANSFER_UUID_2 = UUID.fromString("d063f4bd-dd1f-41d0-8f47-0d5b9195bfaa");

    @Test
    void shouldCreateBankTransferId_whenValueIsValid() {
        var bankTransferId = BankTransferId.of(BANK_TRANSFER_UUID_1);

        assertThat(bankTransferId)
            .isNotNull()
            .extracting(BankTransferId::value)
            .isEqualTo(BANK_TRANSFER_UUID_1);
    }

    @Test
    void shouldReturnTrue_whenComparingWithSameUUID() {
        var bankTransferId = BankTransferId.of(BANK_TRANSFER_UUID_1);
        assertThat(bankTransferId.is(BANK_TRANSFER_UUID_1)).isTrue();
    }

    @Test
    void shouldThrowNullPointerException_whenValueIsNull() {
        assertThatThrownBy(() -> BankTransferId.of(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(BankTransferId.ID_NOT_NULL_MESSAGE);
    }

    @Test
    void shouldBeEqual_whenBankTransferIdsHaveSameValue() {
        var bankTransferId1 = BankTransferId.of(BANK_TRANSFER_UUID_1);
        var bankTransferId2 = BankTransferId.of(BANK_TRANSFER_UUID_1);

        assertThat(bankTransferId1)
            .isEqualTo(bankTransferId2)
            .hasSameHashCodeAs(bankTransferId2);
    }

    @Test
    void shouldNotBeEqual_whenBankTransferIdsHaveDifferentValues() {
        var bankTransferId1 = BankTransferId.of(BANK_TRANSFER_UUID_1);
        var bankTransferId2 = BankTransferId.of(BANK_TRANSFER_UUID_2);

        assertThat(bankTransferId1)
            .isNotEqualTo(bankTransferId2);
    }

    @Test
    void shouldReturnStringRepresentation_whenCallingToString() {
        var bankTransferId = BankTransferId.of(BANK_TRANSFER_UUID_1);
        assertThat(bankTransferId.toString())
            .isEqualTo(BANK_TRANSFER_UUID_1.toString());
    }

}