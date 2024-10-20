package com.example.technical_test.service.impl;

import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import com.example.technical_test.dto.mapper.PersonMapper;
import com.example.technical_test.exception.handler.PersonAlreadyExistsException;
import com.example.technical_test.exception.handler.PersonNotFoundByIdException;
import com.example.technical_test.repository.PersonRepository;
import com.example.technical_test.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public PersonNameWithIdDto createPerson(PersonDataDto requestBody) {
        Optional<Person> personFromRepo = personRepository.findByNameAndDateOfBirth(requestBody.firstName(), requestBody.lastName(), requestBody.dateOfBirth());

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

        return personMapper.entityToPersonDataDto(personRepository.save(person));
    }

    @Override
    public PersonNameWithIdDto getPersonById(Integer id) {
        Person person = getPersonFromRepoById(id);
        return personMapper.entityToDto(person);
    }

    private Person getPersonFromRepoById(Integer id) {
        Optional<Person> personFromRepo = personRepository.findById(id);

        if (personFromRepo.isEmpty()) {
            throw new PersonNotFoundByIdException(id);
        }

        return personFromRepo.get();
    }


}
