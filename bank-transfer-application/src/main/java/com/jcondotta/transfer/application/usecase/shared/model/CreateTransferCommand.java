package com.jcondotta.transfer.application.usecase.shared.model;


import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.PartyRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.PartySender;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryAmount;

import java.time.ZonedDateTime;

public interface CreateTransferCommand {
    PartySender partySender();
    PartyRecipient partyRecipient();
    MonetaryAmount monetaryAmount();
    String reference();
    ZonedDateTime createdAt();
}