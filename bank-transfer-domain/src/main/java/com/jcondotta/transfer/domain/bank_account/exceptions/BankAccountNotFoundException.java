package com.jcondotta.transfer.domain.bank_account.exceptions;

import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;
import com.jcondotta.transfer.domain.shared.exceptions.DomainObjectNotFoundException;

public class BankAccountNotFoundException extends DomainObjectNotFoundException {

    public static final String BANK_ACCOUNT_BY_ACCOUNT_ID_NOT_FOUND_TEMPLATE = "bankAccount.byAccountId.notFound";
    public static final String BANK_ACCOUNT_BY_IBAN_NOT_FOUND_TEMPLATE = "bankAccount.byIban.notFound";

    public BankAccountNotFoundException(BankAccountId bankAccountId) {
        super(BANK_ACCOUNT_BY_ACCOUNT_ID_NOT_FOUND_TEMPLATE, bankAccountId.value());
    }

    public BankAccountNotFoundException(Iban iban) {
        super(BANK_ACCOUNT_BY_IBAN_NOT_FOUND_TEMPLATE, iban.value());
    }
}
