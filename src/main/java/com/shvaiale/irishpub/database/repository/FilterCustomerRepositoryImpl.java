package com.shvaiale.irishpub.database.repository;

import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.dto.CustomerFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterCustomerRepositoryImpl implements FilterCustomerRepository {

    private final EntityManager entityManager;

    @Override
    public List<Customer> findAllByFilter(CustomerFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = cb.createQuery(Customer.class);

        Root<Customer> customer = criteria.from(Customer.class);
        criteria.select(customer);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.name() != null) {
            predicates.add(cb.like(customer.get("name"), filter.name()));
        }
        if (filter.surname() != null) {
            predicates.add(cb.like(customer.get("surname"), filter.surname()));
        }
        if (filter.birthDate() != null) {
            predicates.add(cb.lessThan(customer.get("birthDate"), filter.birthDate()));
        }

        criteria.where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getResultList();
    }
}
