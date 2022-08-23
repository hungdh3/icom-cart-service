package com.icom.cart.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table(name = "customer")
@Entity
public class CustomerEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "customer_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_status", length = 10)
    private String customerStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private static boolean containField(String fieldName) {
        //allows some of field sorted
        return CustomerEntity_.CUSTOMER_EMAIL.equalsIgnoreCase(fieldName) ||
                CustomerEntity_.CREATED_AT.equalsIgnoreCase(fieldName) ||
                CustomerEntity_.UPDATED_AT.equalsIgnoreCase(fieldName);
    }

    public static String getSearchField(String fieldName) {
        //use default search key if input search key is not valid
        return containField(fieldName) ? fieldName : CustomerEntity_.CREATED_AT;
    }
}