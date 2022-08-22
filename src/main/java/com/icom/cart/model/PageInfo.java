package com.icom.cart.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    @ApiModelProperty(
            value = "requested page",
            example = "0",
            required = true
    )
    private int page = 0;

    @ApiModelProperty(
            value = "number of element each page",
            example = "2",
            required = true
    )
    private int elementPerPage = 2;

    @ApiModelProperty(
            value = "sort order (dsc, asc)",
            example = "dsc",
            required = false
    )
    private String direction = "dsc";

    @ApiModelProperty(
            value = "sort key",
            example = "productCategoryName",
            required = false
    )
    private String key;

    public Sort buildSort(String searchKey) {
        switch (direction) {
            case "dsc":
                return Sort.by(searchKey).descending();
            case "asc":
                return Sort.by(searchKey).ascending();
            default:
                log.warn("Invalid direction provided in PageSettings, using descending direction as default value");
                return Sort.by(searchKey).descending();
        }
    }
}
