package com.jcondotta.banktransfer.valueobjects.party;

import com.jcondotta.bank_account.valueobject.BankAccountId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InternalAccountRecipientTest {

    private static final BankAccountId BANK_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());

    @Test
    void shouldCreateRecipientCorrectly_whenUsingBankAccountId() {
        var recipient = InternalAccountRecipient.of(BANK_ACCOUNT_ID);

        assertThat(recipient.bankAccountId()).isEqualTo(BANK_ACCOUNT_ID);
    }

    @Test
    void shouldCreateRecipientCorrectly_whenUsingUUID() {
        var recipient = InternalAccountRecipient.of(BANK_ACCOUNT_ID.value());

        assertThat(recipient.bankAccountId()).isEqualTo(BANK_ACCOUNT_ID);
    }

    @Test
    void shouldThrowNullPointerException_whenBankAccountIdIsNull() {
        assertThatThrownBy(() -> InternalAccountRecipient.of((BankAccountId) null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(PartyRecipient.RECIPIENT_IDENTIFIER_NOT_NULL_MESSAGE);
    }

    @Test
    void shouldThrowNullPointerException_whenUUIDIsNull() {
        assertThatThrownBy(() -> InternalAccountRecipient.of((UUID) null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(BankAccountId.ID_NOT_NULL_MESSAGE);
    }
}
