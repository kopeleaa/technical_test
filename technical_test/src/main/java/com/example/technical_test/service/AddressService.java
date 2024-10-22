package com.example.technical_test.service;

import com.example.technical_test.domain.Address;
import com.example.technical_test.dto.AddressDto;

import java.util.List;

public interface AddressService {

    Address findAddressById(Integer id);

    Address saveAddress(Address address);

    AddressDto createAddress(AddressDto requestBody);

    AddressDto updateAddress(Integer id, AddressDto requestBody);

    AddressDto getAddressById(Integer id);

    List<AddressDto> getAllAddresses();

    void deleteAddressById(Integer id);
}
