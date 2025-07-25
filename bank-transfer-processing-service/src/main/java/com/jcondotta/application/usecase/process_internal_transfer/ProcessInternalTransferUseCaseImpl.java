package com.jcondotta.application.usecase.process_internal_transfer;

import com.jcondotta.bank_account.entity.BankAccount;
import com.jcondotta.banktransfer.entity.BankTransfer;
import com.jcondotta.banktransfer.valueobjects.party.InternalParty;
import com.jcondotta.ports.output.banking.LookupBankAccountFacade;
import com.jcondotta.ports.output.repository.CreateBankTransferRepository;
import com.jcondotta.usecase.process_internal_transfer.ProcessInternalTransferUseCase;
import com.jcondotta.usecase.process_internal_transfer.model.CreateInternalTransferCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Component
@AllArgsConstructor
public class ProcessInternalTransferUseCaseImpl implements ProcessInternalTransferUseCase {

    private final LookupBankAccountFacade lookupBankAccountFacade;
    private final CreateBankTransferRepository createBankTransferRepository;
    private final ExecutorService executorService;
    private final Clock clock;

    @Override
    public void execute(CreateInternalTransferCommand command) {
        var bankAccountSenderFuture = resolveInternalParty(command.partySender());
        var bankAccountRecipientFuture = resolveInternalParty(command.partyRecipient());

        CompletableFuture.allOf(bankAccountSenderFuture, bankAccountRecipientFuture).join();

        BankAccount accountSender = bankAccountSenderFuture.join();
        BankAccount accountRecipient = bankAccountRecipientFuture.join();

        var bankTransfer = BankTransfer.initiateInternalTransfer(
            accountSender.bankAccountId(), accountRecipient.bankAccountId(), command.monetaryAmount(), command.reference(), clock
        );

        createBankTransferRepository.save(bankTransfer);
    }

    private CompletableFuture<BankAccount> resolveInternalParty(InternalParty party) {
        return lookupBankAccountFacade.resolveAsync(party.identifier(), executorService);
    }
}
