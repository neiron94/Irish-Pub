package com.shvaiale.irishpub.database.repository;

import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.dto.CustomerFilter;

import java.util.List;

public interface FilterCustomerRepository {

    List<Customer> findAllByFilter(CustomerFilter filter);
}
