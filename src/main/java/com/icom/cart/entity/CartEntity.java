package com.icom.cart.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Table(name = "cart")
@Entity
public class CartEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "cart_id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    private static boolean containField(String fieldName) {
        //allows some of field sorted
        return CartEntity_.CREATED_AT.equalsIgnoreCase(fieldName);
    }

    public static String getSearchField(String fieldName) {
        //use default search key if input search key is not valid
        return containField(fieldName) ? fieldName : CartEntity_.CREATED_AT;
    }
}