package com.icom.cart.repository;

import com.icom.cart.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartEntityRepository extends JpaRepository<CartEntity, UUID> {
}