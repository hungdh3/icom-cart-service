package com.icom.cart.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

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

    @Column(name = "item_quality")
    private Integer itemQuality;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getItemQuality() {
        return itemQuality;
    }

    public void setItemQuality(Integer itemQuality) {
        this.itemQuality = itemQuality;
    }

    public UUID getItemRef() {
        return itemRef;
    }

    public void setItemRef(UUID itemRef) {
        this.itemRef = itemRef;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}