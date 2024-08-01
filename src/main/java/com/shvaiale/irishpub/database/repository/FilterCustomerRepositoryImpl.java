package com.shvaiale.irishpub.database.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.database.querydsl.QPredicates;
import com.shvaiale.irishpub.dto.CustomerFilter;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.shvaiale.irishpub.database.entity.QCustomer.customer;

@RequiredArgsConstructor
public class FilterCustomerRepositoryImpl implements FilterCustomerRepository {

    private final EntityManager entityManager;

    @Override
    public List<Customer> findAllByFilter(CustomerFilter filter) {
        Predicate predicate = QPredicates.builder()
                .add(filter.name(), customer.name::containsIgnoreCase)
                .add(filter.surname(), customer.surname::containsIgnoreCase)
                .add(filter.birthDate(), customer.birthDate::before)
                .build();

        return new JPAQuery<Customer>(entityManager)
                .select(customer)
                .from(customer)
                .where(predicate)
                .fetch();
    }
}
