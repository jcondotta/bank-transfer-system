package com.jcondotta.banktransfer.valueobjects.party;

import com.jcondotta.bank_account.valueobject.Iban;
import com.jcondotta.banktransfer.valueobjects.party.identifier.InternalAccountIbanIdentifier;
import com.jcondotta.banktransfer.valueobjects.party.identifier.InternalPartyIdentifierType;
import com.jcondotta.testsupport.iban.TestIbanExamples;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InternalIbanRecipientTest {

    private static final Iban VALID_SPANISH_IBAN = Iban.of(TestIbanExamples.VALID_SPAIN);

    @Test
    void shouldCreateRecipientCorrectly_whenUsingIban() {
        var recipient = InternalIbanRecipient.of(VALID_SPANISH_IBAN);

        assertThat(recipient.iban()).isEqualTo(VALID_SPANISH_IBAN);
    }

    @Test
    void shouldCreateRecipientCorrectly_whenUsingString() {
        var recipient = InternalIbanRecipient.of(VALID_SPANISH_IBAN.value());

        assertThat(recipient.iban()).isEqualTo(VALID_SPANISH_IBAN);
    }

    @Test
    void shouldReturnCorrectIdentifier_whenCallingIdentifierMethod() {
        var recipient = InternalIbanRecipient.of(VALID_SPANISH_IBAN);

        assertThat(recipient.identifier())
            .satisfies(identifier -> {
                assertThat(identifier).isInstanceOf(InternalAccountIbanIdentifier.class);
                assertThat(((InternalAccountIbanIdentifier) identifier).iban()).isEqualTo(VALID_SPANISH_IBAN);
                assertThat(identifier.value()).isEqualTo(VALID_SPANISH_IBAN.value());
                assertThat(identifier.type()).isEqualTo(InternalPartyIdentifierType.IBAN);
            });
    }

    @Test
    void shouldThrowNullPointerException_whenIbanIsNull() {
        assertThatThrownBy(() -> InternalIbanRecipient.of((Iban) null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(PartyRecipient.RECIPIENT_IDENTIFIER_NOT_NULL_MESSAGE);
    }

    @Test
    void shouldThrowNullPointerException_whenIbanStringIsNull() {
        assertThatThrownBy(() -> InternalIbanRecipient.of((String) null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(Iban.IBAN_NOT_NULL_MESSAGE);
    }
}
