package com.icom.cart.repository;

import com.icom.cart.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemEntityRepository extends JpaRepository<CartItemEntity, UUID> {
}