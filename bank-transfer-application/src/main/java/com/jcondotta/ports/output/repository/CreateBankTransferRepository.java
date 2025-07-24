package com.jcondotta.ports.output.repository;

import com.jcondotta.banktransfer.entity.BankTransfer;

public interface CreateBankTransferRepository {

    void save(BankTransfer bankTransfer);
}
