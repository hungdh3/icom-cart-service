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
@Table(name = "item_created_event")
@Entity
public class ItemCreatedEventEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "item_created_event_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "item_created_event_hash_content")
    private String itemCreatedEventHashContent;

    @Column(name = "item_created_event_content")
    private String itemCreatedEventContent;

    @Column(name = "item_id")
    private UUID itemId;

    @Column(name = "transaction_id")
    private UUID transactionId;

    @Column(name = "sent")
    private Boolean sent;

    @Column(name = "retry_times")
    private Integer retryTimes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}