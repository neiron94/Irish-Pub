package com.shvaiale.irishpub.database.repository;

import com.shvaiale.irishpub.database.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CustomerRepository extends
        JpaRepository<Customer, Integer>,
        FilterCustomerRepository,
        QuerydslPredicateExecutor<Customer> {
}
