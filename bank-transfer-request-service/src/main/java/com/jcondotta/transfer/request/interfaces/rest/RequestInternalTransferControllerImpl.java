package com.jcondotta.transfer.request.interfaces.rest;

import com.jcondotta.transfer.application.usecase.request_internal_transfer.RequestInternalTransferUseCase;
import com.jcondotta.transfer.request.interfaces.rest.mapper.InternalTransferRestRequestMapper;
import com.jcondotta.transfer.request.interfaces.rest.model.InternalTransferFromAccountIdToAccountIdRestRequest;
import com.jcondotta.transfer.request.interfaces.rest.model.InternalTransferFromAccountIdToIbanRestRequest;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
public class RequestInternalTransferControllerImpl implements RequestInternalTransferController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestInternalTransferControllerImpl.class);

    private final RequestInternalTransferUseCase useCase;
    private final InternalTransferRestRequestMapper requestMapper;

    @Override
    @Timed(
        value = "request.internal.transfer.account-id-to-iban-time",
        description = "Time to process internal transfer from account ID to IBAN",
        percentiles = {0.5, 0.9, 0.95}
    )
    public ResponseEntity<Void> requestInternalBankTransfer(@Valid @RequestBody InternalTransferFromAccountIdToIbanRestRequest request) {
        LOGGER.atInfo()
            .setMessage("Received internal transfer request: senderAccountId={}, recipientIban={}")
            .addArgument(request.senderAccountId())
            .addArgument(request.recipientIban())
            .log();

        useCase.execute(requestMapper.toCommand(request));
        return ResponseEntity.accepted().build();
    }

    @Override
    @Timed(
        value = "request.internal.transfer.account-id-to-account-id-time",
        description = "Time to process internal transfer from account ID to account ID",
        percentiles = {0.5, 0.9, 0.95}
    )
    public ResponseEntity<Void> requestInternalBankTransfer(@Valid @RequestBody InternalTransferFromAccountIdToAccountIdRestRequest request) {
        LOGGER.atInfo()
            .setMessage("Received internal transfer request: senderAccountId={}, recipientAccountId={}")
            .addArgument(request.senderAccountId())
            .addArgument(request.recipientAccountId())
            .log();

        useCase.execute(requestMapper.toCommand(request));
        return ResponseEntity.accepted().build();
    }
}