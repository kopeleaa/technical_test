package com.example.technical_test.service;

import com.example.technical_test.domain.Address;
import com.example.technical_test.dto.AddressDto;

public interface AddressService {

    Address getAddressById(Integer id);

    Address saveAddress(Address address);

    AddressDto createAddress(AddressDto requestBody);
}
