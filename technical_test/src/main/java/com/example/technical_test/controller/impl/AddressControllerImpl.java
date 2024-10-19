package com.example.technical_test.controller.impl;

import com.example.technical_test.controller.AddressController;
import com.example.technical_test.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AddressControllerImpl implements AddressController {

    private final AddressService addressService;

}
