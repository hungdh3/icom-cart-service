package com.icom.cart.model;

import com.icom.cart.validator.UuidValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Slf4j
@Setter
@Getter
public class AddItemToCardRequest {
    @ApiModelProperty(
            value = "Unique ID generated from client to prevent duplicated request, API must handle request in idempotent way with transactionId",
            example = "ed088b36-804c-4f36-b2ac-843a55fd5ec2",
            required = true
    )
    @NotNull
    @UuidValidator
    private String transactionId;

    @ApiModelProperty(
            value = "cardId in UUID form",
            example = "ed088b36-804c-4f36-b2ac-843a55fd5ec2",
            required = true
    )
    @NotNull
    @UuidValidator
    private String cardId;

    @ApiModelProperty(
            value = "userId in UUID form",
            example = "e8035ff8-37f5-4bf1-a8b2-5bfc22bf8631",
            required = true
    )
    @UuidValidator
    private String customerId;

    @NotNull
    @NotEmpty
    private ItemToCart item;
}
