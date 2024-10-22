package com.example.technical_test.service.impl;

import com.example.technical_test.domain.Address;
import com.example.technical_test.domain.ContactInformation;
import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import com.example.technical_test.dto.PersonUpdateDto;
import com.example.technical_test.dto.mapper.PersonMapper;
import com.example.technical_test.enums.ContactInformationType;
import com.example.technical_test.exception.NoEntriesFoundException;
import com.example.technical_test.exception.PersonAlreadyExistsException;
import com.example.technical_test.exception.PersonNotFoundByIdException;
import com.example.technical_test.repository.PersonRepository;
import com.example.technical_test.service.AddressService;
import com.example.technical_test.service.ContactInformationService;
import com.example.technical_test.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final AddressService addressService;
    private final ContactInformationService contactInformationService;

    @Override
    public PersonNameWithIdDto createPerson(PersonDataDto requestBody) {
        Optional<Person> personFromRepo = personRepository
                .findByNameAndDateOfBirth(requestBody.firstName(), requestBody.lastName(), requestBody.dateOfBirth());

        if (personFromRepo.isPresent()) {
            throw new PersonAlreadyExistsException(requestBody.firstName() + " " + requestBody.lastName());
        }

        Person person = personMapper.personDataDtoToEntity(requestBody);

        person.setFirstName(requestBody.firstName());
        person.setLastName(requestBody.lastName());
        person.setDateOfBirth(requestBody.dateOfBirth());

        return personMapper.personNameWithIdDto(personRepository.save(person));
    }

    @Override
    public PersonDataDto updatePersonById(PersonUpdateDto requestBody, Integer id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundByIdException(id));
        Address permAddress = addressService.findAddressById(requestBody.permanentAddressId());
        Address tempAddress = addressService.findAddressById(requestBody.temporaryAddressId());
        ContactInformation phone = new ContactInformation();
        phone.setContactInformationValue(requestBody.phoneNumber());
        phone.setType(ContactInformationType.PHONE_NUMBER);
        ContactInformation email = new ContactInformation();
        email.setContactInformationValue(requestBody.email());
        email.setType(ContactInformationType.EMAIL_ADDRESS);

        permAddress.setPerson(person);
        addressService.saveAddress(permAddress);
        tempAddress.setPerson(person);
        addressService.saveAddress(tempAddress);
        phone.setPerson(person);
        contactInformationService.saveContactInformation(phone);
        email.setPerson(person);
        contactInformationService.saveContactInformation(email);

        person.setFirstName(requestBody.firstName());
        person.setLastName(requestBody.lastName());
        person.setDateOfBirth(requestBody.dateOfBirth());
        person.setPermanentAddress(permAddress);
        person.setTemporaryAddress(tempAddress);
        person.addContactInformation(phone);
        person.addContactInformation(email);

        return personMapper.entityToPersonDataDto(personRepository.save(person));
    }

    @Override
    public PersonNameWithIdDto getPersonById(Integer id) {
        Person person = getPersonFromRepoById(id);
        return personMapper.personNameWithIdDto(person);
    }

    @Override
    public List<PersonNameWithIdDto> getAllPersons() {
        List<Person> people = personRepository.findAll();

        if (people.isEmpty()) {
            throw new NoEntriesFoundException();
        }

        return people.stream().map(personMapper::personNameWithIdDto)
                .toList();
    }

    @Override
    public void deletePerson(Integer id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundByIdException(id));

        personRepository.delete(person);
    }

    public void savePerson(Person person) {
        personRepository.save(person);
    }

    private Person getPersonFromRepoById(Integer id) {
        Optional<Person> personFromRepo = personRepository.findById(id);

        if (personFromRepo.isEmpty()) {
            throw new PersonNotFoundByIdException(id);
        }

        return personFromRepo.get();
    }



}
