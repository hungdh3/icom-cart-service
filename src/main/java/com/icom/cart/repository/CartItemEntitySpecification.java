package com.icom.cart.repository;

import com.icom.cart.entity.CartEntity;
import com.icom.cart.entity.CartItemEntity;
import com.icom.cart.entity.CartItemEntity_;
import org.springframework.data.jpa.domain.Specification;

public class CartItemEntitySpecification {
    public static Specification<CartItemEntity> belongToCart(CartEntity cartEntity) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get(CartItemEntity_.cart), cartEntity);
    }
}
