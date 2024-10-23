package com.example.technical_test.service.impl;

import com.example.technical_test.domain.ContactInformation;
import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.ContactInfoDto;
import com.example.technical_test.dto.ContactUpdateDto;
import com.example.technical_test.dto.mapper.ContactInformationMapper;
import com.example.technical_test.exception.ContactInformationAlreadyExistsException;
import com.example.technical_test.exception.ContactInformationNotFoundByIdException;
import com.example.technical_test.exception.ContactInformationNotFoundByValueException;
import com.example.technical_test.repository.ContactInformationRepository;
import com.example.technical_test.service.ContactInformationService;
import com.example.technical_test.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactInformationServiceImpl implements ContactInformationService {

    private final ContactInformationRepository contactInformationRepository;
    private final ContactInformationMapper contactInformationMapper;
    private final PersonService personService;

    @Override
    public ContactInfoDto createContactInformation(ContactInfoDto requestBody) {
        ContactInformation contactInformation = contactInformationMapper.dtoToEntity(requestBody);
        Person person = contactInformation.getPerson();
        try {
            contactInformationRepository.save(contactInformation);
            person.addContactInformation(contactInformation);
            personService.savePerson(person);
            return contactInformationMapper.entityToDto(contactInformation);
        } catch (DataIntegrityViolationException e) {
            throw new ContactInformationAlreadyExistsException(requestBody.value());
        }
    }

    @Override
    public ContactInfoDto updateContactInformation(ContactUpdateDto requestBody) {
        contactInformationRepository.findById(requestBody.contactInformationId())
                .orElseThrow(() -> new ContactInformationNotFoundByIdException(requestBody.contactInformationId()));



        return null;
    }

    @Override
    public ContactInformation saveContactInformation(ContactInformation contactInformation) {
        return contactInformationRepository.save(contactInformation);
    }

    @Override
    public ContactInformation findContactInformationByValue(String value) {
        Optional<ContactInformation> contactInformation = contactInformationRepository.getContactInformationByValue(value);

        if (contactInformation.isEmpty()) {
            throw new ContactInformationNotFoundByValueException(value);
        }

        return contactInformation.get();
    }
}
