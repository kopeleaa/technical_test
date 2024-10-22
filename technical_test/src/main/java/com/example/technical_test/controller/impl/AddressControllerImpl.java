package com.example.technical_test.controller.impl;

import com.example.technical_test.controller.AddressController;
import com.example.technical_test.dto.AddressDto;
import com.example.technical_test.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Override
    public AddressDto updateAddress(Integer id, AddressDto requestBody) {
        log.info("HTTP request received: PUT /address/{} {}", id, requestBody);
        return addressService.updateAddress(id, requestBody);
    }

    @Override
    public AddressDto getAddressById(Integer id) {
        log.info("HTTP request received: GET /address/{}", id);
        return addressService.getAddressById(id);
    }

    @Override
    public List<AddressDto> getAllAddresses() {
        log.info("HTTP request received: GET /address");
        return addressService.getAllAddresses();
    }

    @Override
    public void deleteAddressById(Integer id) {
        log.info("HTTP request received: DELETE /address/{}", id);
        addressService.deleteAddressById(id);
    }
}
