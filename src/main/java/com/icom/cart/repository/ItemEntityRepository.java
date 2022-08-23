package com.icom.cart.repository;

import com.icom.cart.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemEntityRepository extends JpaRepository<ItemEntity, UUID> {
    boolean existsItemEntitiesByTransactionId(UUID transactionId);
}