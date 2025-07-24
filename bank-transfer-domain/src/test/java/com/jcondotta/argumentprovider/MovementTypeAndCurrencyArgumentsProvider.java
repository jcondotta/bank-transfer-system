package com.jcondotta.argumentprovider;

import com.jcondotta.monetary_movement.enums.MovementType;
import com.jcondotta.shared.valueobjects.Currency;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class MovementTypeAndCurrencyArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
            Arguments.of(MovementType.CREDIT, Currency.EUR),
            Arguments.of(MovementType.CREDIT, Currency.USD),
            Arguments.of(MovementType.DEBIT, Currency.EUR),
            Arguments.of(MovementType.DEBIT, Currency.USD)
        );
    }
}
