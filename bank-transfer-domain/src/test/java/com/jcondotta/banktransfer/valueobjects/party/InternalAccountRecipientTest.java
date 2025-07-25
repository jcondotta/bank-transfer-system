package com.jcondotta.banktransfer.valueobjects.party;

import com.jcondotta.bank_account.valueobject.BankAccountId;
import com.jcondotta.banktransfer.valueobjects.party.identifier.InternalAccountIdIdentifier;
import com.jcondotta.banktransfer.valueobjects.party.identifier.InternalPartyIdentifierType;
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
    void shouldCreateRecipientCorrectly_whenUsingRawString() {
        var rawBankAccountId = BANK_ACCOUNT_ID.value().toString();
        var recipient = InternalAccountRecipient.of(rawBankAccountId);

        assertThat(recipient.bankAccountId()).isEqualTo(BANK_ACCOUNT_ID);
    }

    @Test
    void shouldReturnCorrectIdentifier_whenCallingIdentifierMethod() {
        var recipient = InternalAccountRecipient.of(BANK_ACCOUNT_ID);

        assertThat(recipient.identifier())
            .satisfies(identifier -> {
                assertThat(identifier).isInstanceOf(InternalAccountIdIdentifier.class);
                assertThat(((InternalAccountIdIdentifier) identifier).bankAccountId()).isEqualTo(BANK_ACCOUNT_ID);
                assertThat(identifier.value()).isEqualTo(BANK_ACCOUNT_ID.value().toString());
                assertThat(identifier.type()).isEqualTo(InternalPartyIdentifierType.ACCOUNT_ID);
            });
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
