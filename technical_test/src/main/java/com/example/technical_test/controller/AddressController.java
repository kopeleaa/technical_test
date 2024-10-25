package com.example.technical_test.controller;

import com.example.technical_test.dto.AddressDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/address")
public interface AddressController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    AddressDto createAddress(@Valid @RequestBody AddressDto requestBody);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    AddressDto updateAddress(@PathVariable Integer id, @Valid @RequestBody AddressDto requestBody);

    @PutMapping("/{permAddressId}/{tempAddressId}/{personId}")
    @ResponseStatus(HttpStatus.OK)
    void connectAddressToPerson(@PathVariable Integer permAddressId, @PathVariable Integer tempAddressId, @PathVariable Integer personId);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    AddressDto getAddressById(@PathVariable Integer id);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<AddressDto> getAllAddresses();

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteAddressById(@PathVariable Integer id);

}
