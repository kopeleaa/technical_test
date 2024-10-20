package com.example.technical_test.service.impl;

import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import com.example.technical_test.dto.mapper.PersonMapper;
import com.example.technical_test.exception.NoEntriesFoundException;
import com.example.technical_test.exception.PersonAlreadyExistsException;
import com.example.technical_test.exception.PersonNotFoundByIdException;
import com.example.technical_test.repository.PersonRepository;
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

    @Override
    public PersonNameWithIdDto createPerson(PersonDataDto requestBody) {
        Optional<Person> personFromRepo = personRepository
                .findByNameAndDateOfBirth(requestBody.firstName(), requestBody.lastName(), requestBody.dateOfBirth());

        if (personFromRepo.isPresent()) {
            throw new PersonAlreadyExistsException(requestBody.firstName() + " " + requestBody.lastName());
        }

        Person person = personMapper.dtoToEntity(requestBody);
        return personMapper.entityToDto(personRepository.save(person));
    }

    @Override
    public PersonDataDto updatePersonById(PersonDataDto requestBody, Integer id) {
        Person person = getPersonFromRepoById(id);

        person.setFirstName(requestBody.firstName());
        person.setLastName(requestBody.lastName());
        person.setDateOfBirth(requestBody.dateOfBirth());

        //TODO: addressList, contactInformationList

        return personMapper.entityToPersonDataDto(personRepository.save(person));
    }

    @Override
    public PersonNameWithIdDto getPersonById(Integer id) {
        Person person = getPersonFromRepoById(id);
        return personMapper.entityToDto(person);
    }

    @Override
    public List<PersonNameWithIdDto> getAllPersons() {
        List<Person> people = personRepository.findAll();

        if (people.isEmpty()) {
            throw new NoEntriesFoundException();
        }

        return people.stream().map(personMapper::entityToDto)
                .toList();
    }

    @Override
    public void deletePerson(Integer id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundByIdException(id));

        personRepository.delete(person);
    }

    private Person getPersonFromRepoById(Integer id) {
        Optional<Person> personFromRepo = personRepository.findById(id);

        if (personFromRepo.isEmpty()) {
            throw new PersonNotFoundByIdException(id);
        }

        return personFromRepo.get();
    }



}
