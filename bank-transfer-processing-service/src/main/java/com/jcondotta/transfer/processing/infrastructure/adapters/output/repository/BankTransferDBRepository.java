package com.jcondotta.transfer.processing.infrastructure.adapters.output.repository;

import com.jcondotta.transfer.domain.banktransfer.entity.BankTransfer;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.repository.entity.BankTransferEntity;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.repository.mapper.BankTransferMapper;
import com.jcondotta.transfer.application.ports.output.repository.BankTransferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BankTransferDBRepository implements BankTransferRepository {

    private final SpringBankTransferRepository springBankTransferRepository;
    private final BankTransferMapper bankTransferMapper;

    @Override
    public void save(BankTransfer bankTransfer) {
        List<BankTransferEntity> entities = bankTransferMapper.toEntities(bankTransfer);

        springBankTransferRepository.saveAll(entities);
    }
}
