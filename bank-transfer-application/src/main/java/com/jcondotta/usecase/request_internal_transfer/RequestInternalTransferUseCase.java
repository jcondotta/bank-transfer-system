package com.jcondotta.usecase.request_internal_transfer;

import com.jcondotta.usecase.request_internal_transfer.model.RequestInternalTransferCommand;

/**
 * Use case interface for requesting an internal transfer.
 * This interface defines the contract for executing the request of an internal transfer.
 */
public interface RequestInternalTransferUseCase {

    /**
     * Executes the request for an internal transfer based on the provided command.
     *
     * @param command the command containing the details for the internal transfer request
     */
    void execute(RequestInternalTransferCommand command);
}