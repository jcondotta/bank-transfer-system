package com.jcondotta.transfer.request.interfaces.rest;

import com.jcondotta.transfer.application.ports.output.i18n.MessageResolverPort;
import com.jcondotta.transfer.application.usecase.request_internal_transfer.RequestInternalTransferUseCase;
import com.jcondotta.transfer.application.usecase.shared.model.idempotency.IdempotencyKey;
import com.jcondotta.transfer.request.interfaces.rest.mapper.InternalTransferRestRequestMapper;
import com.jcondotta.transfer.request.interfaces.rest.model.InternalTransferRestRequest;
import io.micrometer.core.annotation.Timed;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Validated
@RestController
public class RequestInternalTransferControllerImpl implements RequestInternalTransferController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestInternalTransferControllerImpl.class);

    private final MessageResolverPort messageResolver;
    private final RequestInternalTransferUseCase useCase;
    private final InternalTransferRestRequestMapper requestMapper;

    public RequestInternalTransferControllerImpl(@Qualifier("infoMessageResolver") MessageResolverPort messageResolver,
                                                 RequestInternalTransferUseCase useCase, InternalTransferRestRequestMapper requestMapper) {
        this.useCase = useCase;
        this.messageResolver = messageResolver;
        this.requestMapper = requestMapper;
    }

    @Override
    @Timed(
        value = "internal.transfer.requested.time",
        description = "Time to process internal transfer from account ID to IBAN",
        percentiles = {0.5, 0.95, 0.99}
    )
    public ResponseEntity<String> requestInternalBankTransfer(InternalTransferRestRequest request, UUID idempotencyKey, Locale locale) {
        LOGGER.atInfo()
            .setMessage("Received internal transfer request: senderAccountId={}, recipientIban={}, idempotencyKey={}")
            .addArgument(request.senderAccountId())
            .addArgument(request.recipientIban())
            .addArgument(idempotencyKey)
            .addKeyValue("idempotencyKey", idempotencyKey)
            .log();

        useCase.execute(requestMapper.toCommand(request), IdempotencyKey.of(idempotencyKey));

        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(messageResolver.resolveMessage("internal.transfer.request.accepted", locale)
              .orElseThrow());
    }
}