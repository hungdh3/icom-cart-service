package com.icom.cart.service;

import com.icom.cart.entity.*;
import com.icom.cart.exception.CartNotFoundException;
import com.icom.cart.kafka.ItemCreatedKafkaProducer;
import com.icom.cart.model.ItemToCart;
import com.icom.cart.model.PageInfo;
import com.icom.cart.repository.CartEntityRepository;
import com.icom.cart.repository.CartItemEntityRepository;
import com.icom.cart.repository.CartItemEntitySpecification;
import com.icom.cart.repository.ItemEntityRepository;
import com.icom.cart.util.JsonUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class CartService {

    @Autowired
    private ItemCreatedKafkaProducer itemCreatedKafkaProducer;
    @Autowired
    private CartEntityRepository cartEntityRepository;
    @Autowired
    private CartItemEntityRepository cartItemEntityRepository;
    @Autowired
    private ItemEntityRepository itemEntityRepository;
    @Autowired
    private ItemCreatedEventService itemCreatedEventService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Page<CartEntity> getCarts(@NonNull PageInfo pageInfo) {
        //handle the case request inputs wrong sort-key
        String searchKey = CartEntity.getSearchField(pageInfo.getKey());
        Sort plantSort = pageInfo.buildSort(searchKey);
        Pageable page = PageRequest.of(pageInfo.getPage(),
                pageInfo.getElementPerPage(),
                plantSort);
        return cartEntityRepository.findAll(page);
    }

    public Page<ItemEntity> getItems(@NonNull PageInfo pageInfo, @NotNull UUID cartId) throws CartNotFoundException {
        Optional<CartEntity> cartEntityOptional = cartEntityRepository.findById(cartId);
        if (cartEntityOptional.isEmpty()) {
            throw new CartNotFoundException("Cart Not Found, id = " + cartId);
        }
        CartEntity cartEntity = cartEntityOptional.get();
        Specification<CartItemEntity> cartItemEntitySpec
                = CartItemEntitySpecification.belongToCart(cartEntity);

        //handle the case request inputs wrong sort-key
        String searchKey = CartEntity.getSearchField(pageInfo.getKey());
        Sort plantSort = pageInfo.buildSort(searchKey);
        Pageable page = PageRequest.of(pageInfo.getPage(),
                pageInfo.getElementPerPage(),
                plantSort);

        return cartItemEntityRepository.findAll(cartItemEntitySpec, page);
    }

    public Optional<CartEntity> cartFromUuidStr(String uuidStr) {
        UUID uuid = UUID.fromString(uuidStr);
        return cartEntityRepository.findById(uuid);
    }

    @Transactional
    public void addItemsToCart(String transactionIdStr,
                               CartEntity cart,
                               CustomerEntity customer,
                               ItemToCart item) {
        UUID transactionID = UUID.fromString(transactionIdStr);
        if (itemEntityRepository.existsItemEntitiesByTransactionId(transactionID)) {
            log.warn("Duplicated Transaction");
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setItemRef(item.getItemRef());
        itemEntity.setItemQuality(item.getItemQuality());
        itemEntity.setCreatedAt(now);
        itemEntity.setTransactionId(transactionID);
        itemEntity.setTransactionStatus(ItemState.ITEM_CREATED.toString());
        itemEntityRepository.save(itemEntity);

        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setCart(cart);
        cartItemEntity.setItem(itemEntity);
        cartItemEntity.setTransactionId(transactionID);
        cartItemEntity.setCreatedAt(now);
        cartItemEntityRepository.save(cartItemEntity);

        ItemCreatedEventEntity itemCreatedEventEntity = itemCreatedEventService.creatEvent(itemEntity);

        try {
            log.info("Publish item created event: " + JsonUtil.objectToString(itemEntity));
            itemCreatedKafkaProducer.sendMessage(
                    JsonUtil.objectToString(itemEntity),
                    itemEntity.getItemRef().toString()); //use item_ref to routing to the same partition.
            itemCreatedEventEntity.setSent(true);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
            itemCreatedEventEntity.setSent(false);
            itemCreatedEventEntity.setRetryTimes(itemCreatedEventEntity.getRetryTimes() + 1);
            //Note: these exceptions won't cause the database transaction to rollback.
        }
        itemCreatedEventService.updateEventSent(itemCreatedEventEntity);
    }
}
