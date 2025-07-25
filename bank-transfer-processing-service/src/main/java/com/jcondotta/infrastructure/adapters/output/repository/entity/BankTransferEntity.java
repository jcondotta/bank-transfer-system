package com.jcondotta.infrastructure.adapters.output.repository.entity;

import com.jcondotta.infrastructure.adapters.output.repository.enums.MovementTypeEntity;
import com.jcondotta.infrastructure.adapters.output.repository.enums.TransferStatusEntity;
import com.jcondotta.infrastructure.adapters.output.repository.enums.TransferTypeEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "bank_transfers")
public class BankTransferEntity {

    @Id
    @Column(name = "bank_transfer_id")
    private UUID bankTransferId;

    @Column(name = "sender_account_id")
    private UUID senderAccountId;

    @Column(name = "recipient_account_id")
    private UUID recipientAccountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false, length = 10)
    private MovementTypeEntity movementType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_type", nullable = false, length = 30)
    private TransferTypeEntity transferType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 15)
    private TransferStatusEntity status;

    @Embedded
    private MonetaryAmountEmbeddable monetaryAmount;

    @Embedded
    private CreatedAtEmbeddable createdAt;

    @Column(name = "reference", length = 50)
    private String reference;

    @Column(name = "bank_transfer_group_id")
    private UUID bankTransferGroupId;
}
