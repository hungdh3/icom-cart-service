package com.icom.cart.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table(name = "item")
@Entity
public class ItemEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "item_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "item_ref", nullable = false)
    private UUID itemRef;

    @Column(name = "transaction_id", nullable = false)
    private UUID transactionId;

    @Column(name = "item_quality")
    @Min(value = 0, message = "The itemQuality must be positive")
    private Integer itemQuality;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private LocalDateTime updatedAt;

}