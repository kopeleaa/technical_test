package com.example.technical_test.service;

import com.example.technical_test.domain.ContactInformation;

public interface ContactInformationService {

    ContactInformation saveContactInformation(ContactInformation contactInformation);

    ContactInformation getContactInformationByValue(String value);
}
