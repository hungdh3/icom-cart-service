package com.icom.cart.repository;

import com.icom.cart.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CartItemEntityRepository
        extends PagingAndSortingRepository<CartItemEntity, UUID>, JpaSpecificationExecutor {
}