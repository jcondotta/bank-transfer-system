package com.jcondotta.transfer.processing.support.instancio;

import com.jcondotta.test_support.iban.TestIbanExamples;
import com.jcondotta.transfer.domain.bank_account.enums.AccountStatus;
import com.jcondotta.transfer.domain.bank_account.enums.AccountType;
import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.client.lookup_bank_account.model.AccountStatusCdo;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.client.lookup_bank_account.model.AccountTypeCdo;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.client.lookup_bank_account.model.BankAccountCdo;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.client.lookup_bank_account.model.CurrencyCdo;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;

public class BankAccountCdoModel {

    public static Model<BankAccountCdo> create(AccountType accountType, AccountStatus status, Currency currency, Iban iban) {
        return Instancio.of(BankAccountCdo.class)
            .set(Select.field("accountType"), AccountTypeCdo.valueOf(accountType.name()))
            .set(Select.field("status"), AccountStatusCdo.valueOf(status.name()))
            .set(Select.field("currency"), CurrencyCdo.valueOf(currency.name()))
            .set(Select.field("iban"), iban.value())
            .toModel();
    }

    public static Model<BankAccountCdo> create(AccountType accountType, AccountStatus status, Currency currency) {
        return create(accountType, status, currency, Iban.of(TestIbanExamples.VALID_ITALY));
    }

    public static Model<BankAccountCdo> create(AccountType accountType, AccountStatus status) {
        return create(accountType, status, Currency.EUR);
    }

    public static Model<BankAccountCdo> create(AccountType accountType) {
        return create(accountType, AccountStatus.ACTIVE);
    }

    public static Model<BankAccountCdo> create() {
        return create(AccountType.CHECKING);
    }
}