package com.jcondotta.banktransfer.valueobjects.party.identifier;

import com.jcondotta.bank_account.valueobject.BankAccountId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InternalAccountIdIdentifierTest {

    private static final BankAccountId BANK_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());

    @Test
    void shouldCreateIdentifier_whenBankAccountIdIsValid() {
        var identifier = InternalAccountIdIdentifier.of(BANK_ACCOUNT_ID);

        assertThat(identifier.bankAccountId()).isEqualTo(BANK_ACCOUNT_ID);
        assertThat(identifier.type()).isEqualTo(InternalPartyIdentifierType.ACCOUNT_ID);
        assertThat(identifier.value()).isEqualTo(BANK_ACCOUNT_ID.toString());
    }

    @Test
    void shouldThrowNullPointerException_whenBankAccountIdIsNull() {
        assertThatThrownBy(() -> InternalAccountIdIdentifier.of(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(BankAccountId.ID_NOT_NULL_MESSAGE);
    }
}
