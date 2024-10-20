package com.example.technical_test.service.impl;

import com.example.technical_test.domain.Address;
import com.example.technical_test.exception.AddressNotFoundByIdException;
import com.example.technical_test.repository.AddressRepository;
import com.example.technical_test.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;


    @Override
    public Address getAddressById(Integer id) {
        Optional<Address> addressFromRepo = addressRepository.findById(id);

        if (addressFromRepo.isEmpty()) {
            throw new AddressNotFoundByIdException(id);
        }
        return addressFromRepo.get();
    }
}
