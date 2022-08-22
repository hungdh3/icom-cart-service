package com.icom.cart.controller;

import com.icom.cart.entity.CustomerEntity;
import com.icom.cart.model.PageInfo;
import com.icom.cart.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "Customer Rest Service")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/v1/cart/customers", produces = "application/json")
    @ApiOperation(value = "Get Customer")
    public ResponseEntity<?> getCustomers(PageInfo page) {
        Page<CustomerEntity> customerEntities = customerService.getCustomers(page);
        log.info("Get Customers");
        //@TODO: return sorted key
        return ResponseEntity.ok(customerEntities);
    }
}
