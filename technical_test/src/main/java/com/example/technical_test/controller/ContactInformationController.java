package com.example.technical_test.controller;

import com.example.technical_test.dto.ContactInfoDto;
import com.example.technical_test.dto.ContactUpdateDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/contact_information")
public interface ContactInformationController {

    @PostMapping()
    ContactInfoDto createContactInformation(@RequestBody ContactInfoDto requestBody);

    @PutMapping
    ContactInfoDto updateContactInformation(@RequestBody ContactUpdateDto requestBody);
}
