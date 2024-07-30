package com.shvaiale.irishpub.database.repository;

import com.shvaiale.irishpub.database.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
