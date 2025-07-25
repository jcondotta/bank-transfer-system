package com.jcondotta.banktransfer.valueobjects.party.identifier;

import com.jcondotta.bank_account.valueobject.Iban;
import com.jcondotta.shared.valueobjects.testdata.TestIbanExamples;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InternalAccountIbanIdentifierTest {

    private static final Iban VALID_ITALIAN_IBAN = Iban.of(TestIbanExamples.VALID_ITALY);

    @Test
    void shouldCreateIdentifier_whenIbanIsValid() {
        var identifier = InternalAccountIbanIdentifier.of(VALID_ITALIAN_IBAN);

        assertThat(identifier.iban()).isEqualTo(VALID_ITALIAN_IBAN);
        assertThat(identifier.type()).isEqualTo(InternalPartyIdentifierType.IBAN);
        assertThat(identifier.value()).isEqualTo(VALID_ITALIAN_IBAN.toString());
    }

    @Test
    void shouldThrowNullPointerException_whenIbanIsNull() {
        assertThatThrownBy(() -> InternalAccountIbanIdentifier.of(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(Iban.IBAN_NOT_NULL_MESSAGE);
    }
}