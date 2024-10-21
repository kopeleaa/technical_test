package com.example.technical_test.service;

import com.example.technical_test.domain.Address;

public interface AddressService {

    Address getAddressById(Integer id);

    void saveAddress(Address address);
}
