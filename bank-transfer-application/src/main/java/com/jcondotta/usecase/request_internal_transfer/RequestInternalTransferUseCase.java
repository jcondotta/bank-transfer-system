package com.jcondotta.usecase.request_internal_transfer;

import com.jcondotta.usecase.request_internal_transfer.model.RequestInternalTransferCommand;

public interface RequestInternalTransferUseCase {

    void execute(RequestInternalTransferCommand command);
}