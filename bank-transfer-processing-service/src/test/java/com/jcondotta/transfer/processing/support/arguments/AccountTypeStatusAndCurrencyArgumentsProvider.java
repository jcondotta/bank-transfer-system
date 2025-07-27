package com.jcondotta.transfer.processing.support.arguments;

import com.jcondotta.transfer.domain.bank_account.enums.AccountStatus;
import com.jcondotta.transfer.domain.bank_account.enums.AccountType;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class AccountTypeStatusAndCurrencyArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(AccountType.values())
            .flatMap(accountType ->
                Stream.of(AccountStatus.values())
                    .flatMap(accountStatus ->
                        Stream.of(Currency.values())
                            .map(currency -> Arguments.of(accountType, accountStatus, currency))
                    )
            );
    }
}
