package com.jcondotta.interfaces.rest;

import com.jcondotta.interfaces.rest.mapper.InternalTransferRestRequestMapper;
import com.jcondotta.interfaces.rest.model.InternalBankTransferRestRequest;
import com.jcondotta.usecase.request_internal_transfer.RequestInternalTransferUseCase;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
public class RequestInternalTransferControllerImpl implements RequestInternalTransferController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestInternalTransferControllerImpl.class);

    private RequestInternalTransferUseCase useCase;
    private InternalTransferRestRequestMapper requestMapper;

    @Override
    public ResponseEntity<Void> createBankAccount(InternalBankTransferRestRequest request) {
        LOGGER.atInfo()
            .setMessage("Received internal transfer request: senderAccountId={}, recipientIban={}")
            .addArgument(request.senderAccountId())
            .addArgument(request.recipientIban())
            .log();

        useCase.execute(requestMapper.toCommand(request));

        return ResponseEntity.accepted().build();
    }
}