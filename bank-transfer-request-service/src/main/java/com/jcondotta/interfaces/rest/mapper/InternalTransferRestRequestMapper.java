package com.jcondotta.interfaces.rest.mapper;

import com.jcondotta.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.banktransfer.valueobjects.party.InternalIbanRecipient;
import com.jcondotta.interfaces.rest.model.InternalBankTransferRestRequest;
import com.jcondotta.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.shared.valueobjects.Currency;
import com.jcondotta.usecase.request_internal_transfer.model.RequestInternalTransferCommand;
import com.jcondotta.usecase.request_internal_transfer.model.RequestInternalTransferFromAccountIdToIbanCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InternalTransferRestRequestMapper {

    default RequestInternalTransferCommand toCommand(InternalBankTransferRestRequest request){
        return RequestInternalTransferFromAccountIdToIbanCommand.of(
            InternalAccountSender.of(request.senderAccountId()),
            InternalIbanRecipient.of(request.recipientIban()),
            MonetaryAmount.of(request.amount(), Currency.valueOf(request.currency())),
            request.reference()
        );
    }
}