package com.example.technical_test.service;

import com.example.technical_test.domain.ContactInformation;
import com.example.technical_test.dto.ContactInfoDto;
import com.example.technical_test.dto.ContactUpdateDto;

public interface ContactInformationService {

    ContactInformation saveContactInformation(ContactInformation contactInformation);

    ContactInformation findContactInformationByValue(String value);

    ContactInfoDto createContactInformation(ContactInfoDto requestBody);

    ContactInfoDto updateContactInformation(ContactUpdateDto requestBody);
}
