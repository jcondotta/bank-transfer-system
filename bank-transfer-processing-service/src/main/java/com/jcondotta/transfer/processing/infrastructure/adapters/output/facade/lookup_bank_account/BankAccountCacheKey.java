package com.jcondotta.transfer.processing.infrastructure.adapters.output.facade.lookup_bank_account;

import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;

public final class BankAccountCacheKey {

    private static final String BANK_ACCOUNT_ID_TEMPLATE = "bankAccounts::%s";

    private BankAccountCacheKey() {}

    public static String of(BankAccountId bankAccountId) {
        return String.format(BANK_ACCOUNT_ID_TEMPLATE, bankAccountId.value().toString());
    }
}