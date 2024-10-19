package com.example.technical_test.service.impl;

import com.example.technical_test.repository.AddressRepository;
import com.example.technical_test.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

}
