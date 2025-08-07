package com.jcondotta.transfer.application.usecase.request_internal_transfer;

import com.jcondotta.transfer.application.usecase.request_internal_transfer.model.RequestInternalTransferCommand;
import com.jcondotta.transfer.application.usecase.shared.model.idempotency.IdempotencyKey;

/**
 * Use case responsible for handling requests to initiate internal bank transfers.
 * <p>
 * This interface defines the contract for executing an internal transfer based on a command
 * and a technical idempotency key that ensures the request is processed only once,
 * even if the client retries the same operation.
 */
public interface RequestInternalTransferUseCase {

    /**
     * Executes the request for an internal bank transfer using the provided command and idempotency key.
     *
     * @param command the command encapsulating the intent and data required for initiating the transfer
     * @param idempotencyKey a technical key used to guarantee that the same request
     *                       (e.g., due to retries or network issues) is processed only once
     */
    void execute(RequestInternalTransferCommand command, IdempotencyKey idempotencyKey);
}
