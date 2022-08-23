package com.icom.cart.service;

import com.icom.cart.entity.ItemCreatedEventEntity;
import com.icom.cart.entity.ItemEntity;
import com.icom.cart.repository.ItemCreatedEventEntityRepository;
import com.icom.cart.util.HashUtil;
import com.icom.cart.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;

@Slf4j
@Service
public class ItemCreatedEventService {
    @Autowired
    private ItemCreatedEventEntityRepository itemCreatedEventEntityRepository;

    @Transactional
    public ItemCreatedEventEntity creatEvent(ItemEntity itemEntity) {
        LocalDateTime now = LocalDateTime.now();
        ItemCreatedEventEntity entity = new ItemCreatedEventEntity();
        entity.setTransactionId(itemEntity.getTransactionId());
        entity.setItemCreatedEventContent(JsonUtil.objectToString(itemEntity));
        entity.setItemId(itemEntity.getItemRef());
        entity.setSent(false);
        entity.setItemCreatedEventHashContent(HashUtil.sha256ToBase64(itemEntity));
        entity.setCreatedAt(now);
        itemCreatedEventEntityRepository.save(entity);
        return entity;
    }

    @Transactional
    public void updateEventSent(ItemCreatedEventEntity entity) {
        entity.setSent(true);
        itemCreatedEventEntityRepository.save(entity);
    }
}
