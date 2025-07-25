package com.jcondotta.infrastructure.adapters.output.repository;

import com.jcondotta.banktransfer.entity.BankTransfer;
import com.jcondotta.infrastructure.adapters.output.repository.entity.BankTransferEntity;
import com.jcondotta.infrastructure.adapters.output.repository.mapper.BankTransferMapper;
import com.jcondotta.ports.output.repository.CreateBankTransferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CreateBankTransferDBRepository implements CreateBankTransferRepository {

    private final SpringBankTransferRepository springBankTransferRepository;
    private final BankTransferMapper bankTransferMapper;

    @Override
    public void save(BankTransfer bankTransfer) {
        List<BankTransferEntity> entities = bankTransferMapper.toEntities(bankTransfer);

        springBankTransferRepository.saveAll(entities);
    }
}
