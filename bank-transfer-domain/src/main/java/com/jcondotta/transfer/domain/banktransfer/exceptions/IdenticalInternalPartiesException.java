package com.jcondotta.transfer.domain.banktransfer.exceptions;

import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalPartyRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalPartySender;
import com.jcondotta.transfer.domain.shared.exceptions.BusinessRuleException;

import java.util.Map;

public class IdenticalInternalPartiesException extends BusinessRuleException {

    public static final String MESSAGE_TEMPLATE = "internal.transfer.identicalParties";

    private final InternalPartySender internalPartySender;
    private final InternalPartyRecipient internalPartyRecipient;

    public IdenticalInternalPartiesException(InternalPartySender internalPartySender, InternalPartyRecipient internalPartyRecipient) {
        super(MESSAGE_TEMPLATE);
        this.internalPartySender = internalPartySender;
        this.internalPartyRecipient = internalPartyRecipient;
    }

    @Override
    public String getType() {
        return "/errors/internal-transfer-identical-parties";
    }

    @Override
    public Map<String, Object> getProperties() {
        return Map.of(
            "partySender", internalPartySender.toString(),
            "partyRecipient", internalPartyRecipient.toString()
        );
    }
}
