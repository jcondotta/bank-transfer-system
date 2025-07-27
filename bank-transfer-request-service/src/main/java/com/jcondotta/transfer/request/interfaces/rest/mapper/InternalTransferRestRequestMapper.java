package com.jcondotta.transfer.request.interfaces.rest.mapper;

import com.jcondotta.transfer.application.usecase.request_internal_transfer.model.RequestInternalTransferCommand;
import com.jcondotta.transfer.application.usecase.request_internal_transfer.model.RequestInternalTransferFromAccountIdToAccountIdCommand;
import com.jcondotta.transfer.application.usecase.request_internal_transfer.model.RequestInternalTransferFromAccountIdToIbanCommand;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalAccountRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalIbanRecipient;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import com.jcondotta.transfer.request.interfaces.rest.model.InternalTransferFromAccountIdToAccountIdRestRequest;
import com.jcondotta.transfer.request.interfaces.rest.model.InternalTransferFromAccountIdToIbanRestRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InternalTransferRestRequestMapper {

    default RequestInternalTransferCommand toCommand(InternalTransferFromAccountIdToIbanRestRequest request){
        return RequestInternalTransferFromAccountIdToIbanCommand.of(
            InternalAccountSender.of(request.senderAccountId()),
            InternalIbanRecipient.of(request.recipientIban()),
            MonetaryAmount.of(request.amount(), Currency.valueOf(request.currency())),
            request.reference()
        );
    }

    default RequestInternalTransferCommand toCommand(InternalTransferFromAccountIdToAccountIdRestRequest request){
        return RequestInternalTransferFromAccountIdToAccountIdCommand.of(
            InternalAccountSender.of(request.senderAccountId()),
            InternalAccountRecipient.of(request.recipientAccountId()),
            MonetaryAmount.of(request.amount(), Currency.valueOf(request.currency())),
            request.reference()
        );
    }
}