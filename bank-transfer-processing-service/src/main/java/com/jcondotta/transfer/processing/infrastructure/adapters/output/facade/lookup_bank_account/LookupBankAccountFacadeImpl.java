package com.jcondotta.transfer.processing.infrastructure.adapters.output.facade.lookup_bank_account;

import com.jcondotta.transfer.domain.bank_account.entity.BankAccount;
import com.jcondotta.transfer.domain.bank_account.exceptions.BankAccountNotFoundException;
import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.client.lookup_bank_account.LookupBankAccountClient;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.client.lookup_bank_account.model.BankAccountCdo;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.facade.lookup_bank_account.mapper.LookupBankAccountCdoFacadeMapper;
import com.jcondotta.transfer.application.ports.output.banking.LookupBankAccountFacade;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Slf4j
@Component
@AllArgsConstructor
public class LookupBankAccountFacadeImpl implements LookupBankAccountFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(LookupBankAccountFacadeImpl.class);

    private final LookupBankAccountClient client;
    private final LookupBankAccountCdoFacadeMapper mapper;

    @Override
    public BankAccount byId(BankAccountId bankAccountId) {
        return fetch(
            () -> client.findById(bankAccountId.value()).bankAccountCdo(),
            () -> new BankAccountNotFoundException(bankAccountId),
            bankAccountId
        );
    }

    @Override
    public BankAccount byIban(Iban iban) {
        return fetch(
            () -> client.findByIban(iban.value()).bankAccountCdo(),
            () -> new BankAccountNotFoundException(iban),
            iban
        );
    }

    private <T> BankAccount fetch(
        Supplier<Object> call,
        Supplier<BankAccountNotFoundException> notFoundException,
        T identifier
    ) {
        try {
            var cdo = call.get();
            return mapper.map((BankAccountCdo) cdo);
        } catch (FeignException.NotFound e) {
            LOGGER.warn("Bank account not found: {}", identifier);
            throw notFoundException.get();
        } catch (FeignException.InternalServerError e) {
            LOGGER.error("Internal server error while fetching bank account: {}. Reason: {}", identifier, e.getMessage(), e);
            throw new RuntimeException("Internal error on lookup", e);
        } catch (FeignException e) {
            LOGGER.error("Unexpected Feign error while fetching bank account: {}. Status: {}, Message: {}", identifier, e.status(), e.getMessage(), e);
            throw new RuntimeException("Unexpected error on lookup", e);
        }
    }
}


