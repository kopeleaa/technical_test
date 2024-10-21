package com.example.technical_test.controller;

import com.example.technical_test.dto.AddressDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/address")
public interface AddressController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AddressDto createAddress(@RequestBody AddressDto requestBody);



}
