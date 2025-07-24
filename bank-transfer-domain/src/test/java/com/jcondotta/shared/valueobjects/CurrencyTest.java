package com.jcondotta.shared.valueobjects;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyTest {

    private record CurrencyDetails(Currency currency, String expectedSymbol, String expectedDescription) {}

    @ParameterizedTest
    @MethodSource("currencyDetailsProvider")
    void shouldReturnCorrectCurrencyDetails_whenCurrencyIsMapped(CurrencyDetails details) {
        assertThat(details.currency().symbol()).isEqualTo(details.expectedSymbol());
        assertThat(details.currency().description()).isEqualTo(details.expectedDescription());
    }

    private static Stream<CurrencyDetails> currencyDetailsProvider() {
        return Stream.of(
            new CurrencyDetails(Currency.EUR, "€", "Euro"),
            new CurrencyDetails(Currency.USD, "$", "US Dollar"),
            new CurrencyDetails(Currency.GBP, "£", "British Pound")
        );
    }
}
