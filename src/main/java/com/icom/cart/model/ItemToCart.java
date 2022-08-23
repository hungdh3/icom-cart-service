package com.icom.cart.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemToCart {
    @ApiModelProperty(
            value = "Number of Item that user wants to add to cart",
            example = "3",
            required = true
    )
    @Min(value = 0, message = "The itemQuality must be positive")
    private Integer itemQuality;

    @ApiModelProperty(
            value = "The id of product or service... that user wants to add to cart",
            example = "3",
            required = true
    )
    @NotNull
    private UUID itemRef;
}
