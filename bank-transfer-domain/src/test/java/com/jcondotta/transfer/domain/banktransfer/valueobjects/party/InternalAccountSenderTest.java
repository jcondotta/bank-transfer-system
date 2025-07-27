package com.jcondotta.transfer.domain.banktransfer.valueobjects.party;

import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.PartySender;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.identifier.InternalAccountIdIdentifier;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.identifier.InternalPartyIdentifierType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InternalAccountSenderTest {

    private static final BankAccountId BANK_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());

    @Test
    void shouldCreateSenderCorrectly_whenUsingBankAccountId() {
        var sender = InternalAccountSender.of(BANK_ACCOUNT_ID);

        assertThat(sender.bankAccountId()).isEqualTo(BANK_ACCOUNT_ID);
    }

    @Test
    void shouldCreateSenderCorrectly_whenUsingUUID() {
        var accountSender = InternalAccountSender.of(BANK_ACCOUNT_ID.value());

        assertThat(accountSender.bankAccountId()).isEqualTo(BANK_ACCOUNT_ID);
    }

    @Test
    void shouldReturnCorrectIdentifier_whenCallingIdentifierMethod() {
        var accountSender = InternalAccountSender.of(BANK_ACCOUNT_ID);

        assertThat(accountSender.identifier())
            .satisfies(identifier -> {
                assertThat(identifier).isInstanceOf(InternalAccountIdIdentifier.class);
                assertThat(((InternalAccountIdIdentifier) identifier).bankAccountId()).isEqualTo(BANK_ACCOUNT_ID);
                assertThat(identifier.value()).isEqualTo(BANK_ACCOUNT_ID.value().toString());
                assertThat(identifier.type()).isEqualTo(InternalPartyIdentifierType.ACCOUNT_ID);
            });
    }

    @Test
    void shouldThrowNullPointerException_whenBankAccountIdIsNull() {
        assertThatThrownBy(() -> InternalAccountSender.of((BankAccountId) null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(PartySender.SENDER_IDENTIFIER_NOT_NULL_MESSAGE);
    }

    @Test
    void shouldThrowNullPointerException_whenUUIDIsNull() {
        assertThatThrownBy(() -> InternalAccountSender.of((UUID) null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(BankAccountId.ID_NOT_NULL_MESSAGE);
    }
}
