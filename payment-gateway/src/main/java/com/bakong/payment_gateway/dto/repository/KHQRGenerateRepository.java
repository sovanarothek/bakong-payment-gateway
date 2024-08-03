package com.bakong.payment_gateway.dto.repository;

import com.bakong.payment_gateway.dto.entity.KHQRGenerateEntity;
import com.bakong.payment_gateway.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface KHQRGenerateRepository extends JpaRepository<KHQRGenerateEntity, Long> {

    List<KHQRGenerateEntity> findAllByTransactionStatus(TransactionStatus transactionStatus);

    @Query("UPDATE KHQRGenerateEntity k SET k.transactionStatus = :transactionStatus WHERE k.id = :id")
    @Modifying
    @Transactional
    void updateTransactionStatusById(Long id, TransactionStatus transactionStatus);
}
