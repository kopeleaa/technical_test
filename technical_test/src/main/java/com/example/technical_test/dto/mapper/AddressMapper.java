package com.example.technical_test.dto.mapper;

import com.example.technical_test.domain.Address;
import com.example.technical_test.dto.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address dtoToEntity(AddressDto dto) {
        Address address = new Address();

        address.setZipCode(dto.zipCode());
        address.setCity(dto.city());
        address.setStreet(dto.street());
        address.setHouseNumber(dto.houseNumber());

        return address;
    }

    public AddressDto entityToDto(Address address) {
        return AddressDto.builder()
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .build();
    }
}
