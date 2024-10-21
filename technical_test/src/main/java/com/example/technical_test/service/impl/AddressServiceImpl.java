package com.example.technical_test.service.impl;

import com.example.technical_test.domain.Address;
import com.example.technical_test.dto.AddressDto;
import com.example.technical_test.dto.mapper.AddressMapper;
import com.example.technical_test.exception.AddressAlreadyExistsException;
import com.example.technical_test.exception.AddressNotFoundByIdException;
import com.example.technical_test.repository.AddressRepository;
import com.example.technical_test.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;


    @Override
    public Address getAddressById(Integer id) {
        Optional<Address> addressFromRepo = addressRepository.findById(id);

        if (addressFromRepo.isEmpty()) {
            throw new AddressNotFoundByIdException(id);
        }
        return addressFromRepo.get();
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public AddressDto createAddress(AddressDto requestBody) {
        Address address = addressMapper.dtoToEntity(requestBody);
        try {
            return addressMapper.entityToDto(addressRepository.save(address));
        } catch (DataIntegrityViolationException e) {
            throw new AddressAlreadyExistsException(requestBody.city());
        }
    }
}
