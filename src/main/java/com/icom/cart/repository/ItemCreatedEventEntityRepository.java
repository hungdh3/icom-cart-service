package com.icom.cart.repository;

import com.icom.cart.entity.ItemCreatedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemCreatedEventEntityRepository extends JpaRepository<ItemCreatedEventEntity, UUID> {
}