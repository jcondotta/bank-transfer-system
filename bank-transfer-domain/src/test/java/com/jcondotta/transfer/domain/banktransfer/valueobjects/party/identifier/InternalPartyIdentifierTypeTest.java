package com.jcondotta.transfer.domain.banktransfer.valueobjects.party.identifier;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InternalPartyIdentifierTypeTest {

    @Test
    void shouldReturnAccountId_whenFromIsCalledWithAccountId() {
        var result = InternalPartyIdentifierType.from("ACCOUNT_ID");

        assertThat(result).isEqualTo(InternalPartyIdentifierType.ACCOUNT_ID);
    }

    @Test
    void shouldReturnIban_whenFromIsCalledWithIban() {
        var result = InternalPartyIdentifierType.from("IBAN");

        assertThat(result).isEqualTo(InternalPartyIdentifierType.IBAN);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenFromIsCalledWithInvalidValue() {
        assertThatThrownBy(() -> InternalPartyIdentifierType.from("invalid"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unknown InternalPartyIdentifierType: invalid");
    }
}
