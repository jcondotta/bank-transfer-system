package com.jcondotta.transfer.request.application.usecase.request_internal_transfer;

import com.jcondotta.transfer.application.ports.output.cache.CacheStore;
import com.jcondotta.transfer.application.ports.output.messaging.request_internal_transfer.InternalTransferRequestedMessageProducer;
import com.jcondotta.transfer.application.usecase.request_internal_transfer.RequestInternalTransferUseCase;
import com.jcondotta.transfer.application.usecase.request_internal_transfer.mapper.RequestInternalTransferCommandMapper;
import com.jcondotta.transfer.application.usecase.request_internal_transfer.model.RequestInternalTransferCommand;
import com.jcondotta.transfer.application.usecase.shared.model.idempotency.IdempotencyKey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Slf4j
@Service
@AllArgsConstructor
public class RequestInternalTransferUseCaseImpl implements RequestInternalTransferUseCase {

    private final InternalTransferRequestedMessageProducer messageProducer;
    private final RequestInternalTransferCommandMapper commandMapper;
    private final Clock clock;

    private CacheStore<Object> cacheStore;

    @Override
    public void execute(RequestInternalTransferCommand command, IdempotencyKey idempotencyKey) {
        log.info("➡️ Executing internal transfer request [sender={}, recipient={}, amount={}, currency={}, idempotencyKey={}]",
            command.partySender().identifier().value(),
            command.partyRecipient().identifier().value(),
            command.monetaryAmount().amount().toPlainString(),
            command.monetaryAmount().currency(),
            idempotencyKey.value()
        );

        var idempotencyKeyCacheKey = "idempotency:" + idempotencyKey.value();
        if(cacheStore.get(idempotencyKeyCacheKey) == null) {
            var transferRequestedEvent = commandMapper.toEvent(command, idempotencyKey, clock);
            messageProducer.send(transferRequestedEvent);
            cacheStore.put(idempotencyKeyCacheKey, DigestUtils.sha256Hex(command.toString()));
        }
        else {
            log.info("Idempotency key '{}' already processed, skipping transfer request.", idempotencyKey.value());
        }
    }
}
