package com.jcondotta.transfer.application.ports.output.repository;

import com.jcondotta.transfer.domain.banktransfer.entity.BankTransfer;

public interface BankTransferRepository {

    void save(BankTransfer bankTransfer);
}
