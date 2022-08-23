package com.icom.cart.controller;

import com.icom.cart.entity.CartEntity;
import com.icom.cart.entity.CustomerEntity;
import com.icom.cart.entity.ItemEntity;
import com.icom.cart.exception.CartNotFoundException;
import com.icom.cart.model.AddItemToCardRequest;
import com.icom.cart.model.ErrorResponse;
import com.icom.cart.model.PageInfo;
import com.icom.cart.service.CartService;
import com.icom.cart.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "Cart Rest Service")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping(value = "/v1/carts", produces = "application/json")
    @ApiOperation(value = "Get Carts")
    public ResponseEntity<?> getCarts(PageInfo page) {
        Page<CartEntity> cartEntities = cartService.getCarts(page);
        log.info("Get Carts");
        //@TODO: return sorted key
        return ResponseEntity.ok(cartEntities);
    }

    @GetMapping(value = "/v1/cart/items", produces = "application/json")
    @ApiOperation(value = "Get Items of Cart")
    public ResponseEntity<?> getItemsOfCart(PageInfo page, String cartUUID) {
        log.info("Get Items from CardID: " + cartUUID);
        UUID cartId = UUID.fromString(cartUUID);
        try {
            Page<ItemEntity> cartEntities = cartService.getItems(page, cartId);
            return ResponseEntity.ok(cartEntities);
        } catch (CartNotFoundException e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/v1/cart/items", produces = "application/json")
    @ApiOperation(value = "Add items to exist Cart")
    public ResponseEntity<?> addItemsToCart(
            @Valid @RequestBody AddItemToCardRequest addItemToCardRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        Optional<CustomerEntity> customerEntityOptional = customerService.customerFromUuidStr(addItemToCardRequest.getCustomerId());
        if (customerEntityOptional.isEmpty()) {
            errorResponse.setMessage("Customer doesn't exist: " + addItemToCardRequest.getCustomerId());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Optional<CartEntity> cartEntityOptional = cartService.cartFromUuidStr(addItemToCardRequest.getCardId());
        if (cartEntityOptional.isEmpty()) {
            errorResponse.setMessage("Cart doesn't exist: " + addItemToCardRequest.getCardId());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if (!cartEntityOptional.get().getCustomer().getId().equals(customerEntityOptional.get().getId())) {
            errorResponse.setMessage("Cart id: " + addItemToCardRequest.getCardId() +
                    " doesn't belong to Customer id: " + customerEntityOptional.get().getId());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        cartService.addItemsToCart(
                addItemToCardRequest.getTransactionId(),
                cartEntityOptional.get(),
                customerEntityOptional.get(),
                addItemToCardRequest.getItem());
        return ResponseEntity.ok(cartEntityOptional.get());
    }
}
