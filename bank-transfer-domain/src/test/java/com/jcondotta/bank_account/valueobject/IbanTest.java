package com.jcondotta.bank_account.valueobject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IbanTest {

    private static final String IBAN_SPAIN = "ES3801283316232166447417";
    private static final String IBAN_ITALY = "IT93Q0300203280175171887193";

    @Test
    void shouldCreateIban_whenValueIsValid() {
        assertThat(Iban.of(IBAN_SPAIN))
            .isNotNull()
            .extracting(Iban::value)
            .isEqualTo(IBAN_SPAIN);
    }

    @Test
    void shouldReturnTrue_whenComparingWithSameValue() {
        var valueObject = Iban.of(IBAN_SPAIN);
        assertThat(valueObject.is(IBAN_SPAIN)).isTrue();
    }


    @Test
    void shouldThrowNullPointerException_whenValueIsNull() {
        assertThatThrownBy(() -> Iban.of(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(Iban.IBAN_VALUE_NOT_NULL_MESSAGE);
    }

    @Test
    void shouldBeEqual_whenTwoObjectsHaveSameValue() {
        var iban1 = Iban.of(IBAN_SPAIN);
        var iban2 = Iban.of(IBAN_SPAIN);

        assertThat(iban1)
            .isEqualTo(iban2)
            .hasSameHashCodeAs(iban2);
    }

    @Test
    void shouldNotBeEqual_whenTwoObjectsHaveDifferentValues() {
        var iban1 = Iban.of(IBAN_SPAIN);
        var iban2 = Iban.of(IBAN_ITALY);

        assertThat(iban1)
            .isNotEqualTo(iban2);
    }

    @Test
    void shouldReturnStringRepresentation_whenCallingToString() {
        var iban = Iban.of(IBAN_SPAIN);
        assertThat(iban.toString()).isEqualTo(IBAN_SPAIN);
    }
}