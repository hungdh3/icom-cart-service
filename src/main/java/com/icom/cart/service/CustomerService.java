package com.icom.cart.service;

import com.icom.cart.entity.CustomerEntity;
import com.icom.cart.model.PageInfo;
import com.icom.cart.repository.CustomerEntityRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustomerEntityRepository customerEntityRepository;

    public Page<CustomerEntity> getCustomers(@NonNull PageInfo pageInfo) {
        //handle the case request inputs wrong sort-key
        String searchKey = CustomerEntity.getSearchField(pageInfo.getKey());
        Sort plantSort = pageInfo.buildSort(searchKey);
        Pageable page = PageRequest.of(pageInfo.getPage(),
                pageInfo.getElementPerPage(),
                plantSort);
        return customerEntityRepository.findAll(page);
    }
}
