package com.example.technical_test.controller;

import com.example.technical_test.dto.AddressDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/address")
public interface AddressController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AddressDto createAddress(@RequestBody AddressDto requestBody);

    @PutMapping("/{id}")
    AddressDto updateAddress(@PathVariable Integer id, @RequestBody AddressDto requestBody);

    @PutMapping("/{permAddressId}/{tempAddressId}/{personId}")
    void connectAddressToPerson(@PathVariable Integer permAddressId, @PathVariable Integer tempAddressId, @PathVariable Integer personId);

    @GetMapping("/{id}")
    AddressDto getAddressById(@PathVariable Integer id);

    @GetMapping
    List<AddressDto> getAllAddresses();

    @DeleteMapping("/{id}")
    void deleteAddressById(@PathVariable Integer id);

}
