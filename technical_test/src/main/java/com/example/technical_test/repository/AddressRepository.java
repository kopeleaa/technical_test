package com.example.technical_test.repository;

import com.example.technical_test.domain.Address;
import org.springframework.data.repository.ListCrudRepository;

public interface AddressRepository extends ListCrudRepository<Address, Integer> {
}
