package com.jcondotta.infrastructure.adapters.output.client.lookup_bank_account;

import com.jcondotta.infrastructure.adapters.output.client.lookup_bank_account.model.LookupBankAccountResponseCdo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "bankAccountClient", url = "${client.api.bank-accounts.base-url}")
public interface LookupBankAccountClient {

    @GetMapping("/api/v1/bank-accounts/{bankAccountId}")
    LookupBankAccountResponseCdo findById(@PathVariable UUID bankAccountId);

    @GetMapping(value = "/api/v1/bank-accounts", params = "iban")
    LookupBankAccountResponseCdo findByIban(@RequestParam("iban") String iban);
}