package com.jcondotta.usecase.process_internal_transfer;


import com.jcondotta.usecase.process_internal_transfer.model.CreateInternalTransferCommand;

/**
 * Use case interface for creating an internal transfer.
 * This interface defines the contract for executing the creation of an internal transfer.
 */
public interface ProcessInternalTransferUseCase {

    /**
     * Executes the creation of an internal transfer based on the provided command.
     *
     * @param command the command containing the details for the internal transfer
     */
    void execute(CreateInternalTransferCommand command);
}