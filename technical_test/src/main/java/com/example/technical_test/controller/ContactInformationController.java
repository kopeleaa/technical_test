package com.example.technical_test.controller;

import com.example.technical_test.dto.ContactInfoDto;
import com.example.technical_test.dto.ContactUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/contact_information")
public interface ContactInformationController {

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    ContactInfoDto createContactInformation(@Valid @RequestBody ContactInfoDto requestBody);

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    ContactInfoDto updateContactInformation(@Valid @RequestBody ContactUpdateDto requestBody);
}
