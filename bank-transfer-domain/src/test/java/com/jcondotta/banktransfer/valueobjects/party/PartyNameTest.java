package com.jcondotta.banktransfer.valueobjects.party;

import com.jcondotta.argumentprovider.BlankValuesArgumentProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PartyNameTest {

    private static final String PARTY_NAME_JEFFERSON = "Jefferson Condotta";
    private static final String PARTY_NAME_PATRIZIO = "Patrizio Condotta";

    @Test
    void shouldCreatePartyName_whenValueIsValid() {
        assertThat(PartyName.of(PARTY_NAME_JEFFERSON))
            .isNotNull()
            .extracting(PartyName::value)
            .isEqualTo(PARTY_NAME_JEFFERSON);
    }

    @Test
    void shouldThrowNullPointerException_whenValueIsNull() {
        assertThatThrownBy(() -> PartyName.of(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(PartyName.NAME_NOT_NULL_MESSAGE);
    }

    @ParameterizedTest
    @ArgumentsSource(BlankValuesArgumentProvider.class)
    void shouldThrowIllegalArgumentException_whenNameIsBlank(String blankValue) {
        assertThatThrownBy(() -> PartyName.of(blankValue))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(PartyName.NAME_NOT_EMPTY_MESSAGE);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenNameIsTooLong() {
        var longName = "A".repeat(256);

        assertThatThrownBy(() -> PartyName.of(longName))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(PartyName.NAME_TOO_LONG_MESSAGE);
    }

    @Test
    void shouldBeEqual_whenNamesHaveSameValue() {
        var partyName1 = PartyName.of(PARTY_NAME_JEFFERSON);
        var partyName2 = PartyName.of(PARTY_NAME_JEFFERSON);

        assertThat(partyName1)
            .isEqualTo(partyName2)
            .hasSameHashCodeAs(partyName2);
    }

    @Test
    void shouldNotBeEqual_whenNamesHaveDifferentValues() {
        var partyName1 = PartyName.of(PARTY_NAME_JEFFERSON);
        var partyName2 = PartyName.of(PARTY_NAME_PATRIZIO);

        assertThat(partyName1)
            .isNotEqualTo(partyName2);
    }
}