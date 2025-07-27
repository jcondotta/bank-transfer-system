package com.jcondotta.transfer.processing.application.usecase.internal_transfer;

import com.jcondotta.transfer.domain.bank_account.entity.BankAccount;
import com.jcondotta.transfer.domain.banktransfer.entity.BankTransfer;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalParty;
import com.jcondotta.transfer.application.ports.output.banking.LookupBankAccountFacade;
import com.jcondotta.transfer.application.ports.output.repository.BankTransferRepository;
import com.jcondotta.transfer.application.usecase.process_internal_transfer.ProcessInternalTransferUseCase;
import com.jcondotta.transfer.application.usecase.process_internal_transfer.model.CreateInternalTransferCommand;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Component
@AllArgsConstructor
public class ProcessInternalTransferUseCaseImpl implements ProcessInternalTransferUseCase {

    private final LookupBankAccountFacade lookupBankAccountFacade;
    private final BankTransferRepository bankTransferRepository;
    private final ExecutorService executorService;
    private final Clock clock;

    @Override
    @Timed(
        value = "process.internal.transfer.time",
        description = "Time taken to process internal bank transfer",
        percentiles = {0.5, 0.9, 0.95}
    )
    public void execute(CreateInternalTransferCommand command) {
        var bankAccountSenderFuture = resolveInternalParty(command.partySender());
        var bankAccountRecipientFuture = resolveInternalParty(command.partyRecipient());

        CompletableFuture.allOf(bankAccountSenderFuture, bankAccountRecipientFuture).join();

        BankAccount accountSender = bankAccountSenderFuture.join();
        BankAccount accountRecipient = bankAccountRecipientFuture.join();

        var bankTransfer = BankTransfer.initiateInternalTransfer(
            accountSender.bankAccountId(), accountRecipient.bankAccountId(), command.monetaryAmount(), command.reference(), clock
        );

        bankTransferRepository.save(bankTransfer);
    }

    private CompletableFuture<BankAccount> resolveInternalParty(InternalParty party) {
        return lookupBankAccountFacade.resolveAsync(party.identifier(), executorService);
    }
}
