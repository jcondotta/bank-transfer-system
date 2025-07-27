package com.jcondotta.transfer.domain.bank_account.valueobject;

import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;
import org.junit.jupiter.api.Test;

import static com.jcondotta.test_support.iban.TestIbanExamples.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IbanTest {

    @Test
    void shouldCreateIban_whenValueIsValid() {
        var iban = Iban.of(VALID_SPAIN);
        assertThat(iban.value()).isEqualTo(VALID_SPAIN);
    }

    @Test
    void shouldCreateSanitizedIban_whenIbanValueIsMessy() {
        var iban = Iban.of(VALID_ITALY_LOWERCASE_WITH_SPACES);
        var expectedSanitizedIban = VALID_ITALY_LOWERCASE_WITH_SPACES.replaceAll("\\s", "").toUpperCase();

        assertThat(iban.value()).isEqualTo(expectedSanitizedIban);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenIbanIsInvalid() {
        assertThatThrownBy(() -> Iban.of(INVALID_FORMAT))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(Iban.IBAN_INVALID_MESSAGE);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenIbanChecksumIsInvalid() {
        assertThatThrownBy(() -> Iban.of(INVALID_CHECKSUM))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(Iban.IBAN_INVALID_MESSAGE);
    }

    @Test
    void shouldThrowNullPointerException_whenIbanValueIsNull() {
        assertThatThrownBy(() -> Iban.of(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(Iban.IBAN_NOT_NULL_MESSAGE);
    }

    @Test
    void shouldBeEqual_whenTwoObjectsHaveSameValue() {
        var iban1 = Iban.of(VALID_SPAIN);
        var iban2 = Iban.of(VALID_SPAIN);

        assertThat(iban1)
            .isEqualTo(iban2)
            .hasSameHashCodeAs(iban2);
    }

    @Test
    void shouldNotBeEqual_whenTwoObjectsHaveDifferentValues() {
        var iban1 = Iban.of(VALID_SPAIN);
        var iban2 = Iban.of(VALID_ITALY);

        assertThat(iban1)
            .isNotEqualTo(iban2);
    }

    @Test
    void shouldReturnStringRepresentation_whenCallingToString() {
        var iban = Iban.of(VALID_SPAIN);
        assertThat(iban.toString()).isEqualTo(VALID_SPAIN);
    }
}