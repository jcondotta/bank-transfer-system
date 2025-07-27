package com.jcondotta.transfer.processing.infrastructure.adapters.output.facade.lookup_bank_account.mapper;

import com.jcondotta.transfer.domain.bank_account.entity.BankAccount;
import com.jcondotta.transfer.domain.bank_account.enums.AccountStatus;
import com.jcondotta.transfer.domain.bank_account.enums.AccountType;
import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.client.lookup_bank_account.model.AccountStatusCdo;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.client.lookup_bank_account.model.AccountTypeCdo;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.client.lookup_bank_account.model.BankAccountCdo;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.client.lookup_bank_account.model.CurrencyCdo;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface LookupBankAccountCdoFacadeMapper {

    LookupBankAccountCdoFacadeMapper INSTANCE = Mappers.getMapper(LookupBankAccountCdoFacadeMapper.class);

    @Mapping(target = "bankAccountId", source = "bankAccountId")
    @Mapping(target = "accountType", source = "accountType")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "iban", source = "iban")
    @Mapping(target = "createdAt", source = "dateOfOpening")
    BankAccount map(BankAccountCdo bankAccountCdo);

    default BankAccountId map(UUID bankAccountId) {
        return BankAccountId.of(bankAccountId);
    }

    default AccountType map(AccountTypeCdo cdo) {
        return AccountType.valueOf(cdo.name());
    }

    default Currency map(CurrencyCdo cdo) {
        return Currency.valueOf(cdo.name());
    }

    default AccountStatus map(AccountStatusCdo cdo) {
        return AccountStatus.valueOf(cdo.name());
    }

    default Iban map(String value) {
        return Iban.of(value);
    }

}
