package com.example.technical_test.service.impl;

import com.example.technical_test.domain.ContactInformation;
import com.example.technical_test.exception.ContactInformationNotFoundByValueException;
import com.example.technical_test.repository.ContactInformationRepository;
import com.example.technical_test.service.ContactInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactInformationServiceImpl implements ContactInformationService {

    private final ContactInformationRepository contactInformationRepository;

    @Override
    public ContactInformation saveContactInformation(ContactInformation contactInformation) {
        return contactInformationRepository.save(contactInformation);
    }

    @Override
    public ContactInformation getContactInformationByValue(String value) {
        Optional<ContactInformation> contactInformation = contactInformationRepository.getContactInformationByValue(value);

        if (contactInformation.isEmpty()) {
            throw new ContactInformationNotFoundByValueException(value);
        }

        return contactInformation.get();
    }
}
