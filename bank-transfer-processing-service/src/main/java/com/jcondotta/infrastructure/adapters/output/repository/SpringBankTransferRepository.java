package com.jcondotta.infrastructure.adapters.output.repository;

import com.jcondotta.infrastructure.adapters.output.repository.entity.BankTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringBankTransferRepository extends JpaRepository<BankTransferEntity, UUID> {

}
