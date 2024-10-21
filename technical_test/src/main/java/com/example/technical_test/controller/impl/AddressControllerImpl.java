package com.example.technical_test.controller.impl;

import com.example.technical_test.controller.AddressController;
import com.example.technical_test.dto.AddressDto;
import com.example.technical_test.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AddressControllerImpl implements AddressController {

    private final AddressService addressService;

    @Override
    public AddressDto createAddress(AddressDto requestBody) {
        log.info("HTTP request received: POST /address {}", requestBody);
        return addressService.createAddress(requestBody);
    }
}
