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

        Person person = personMapper.personDataDtoToEntity(requestBody);

        person.setFirstName(requestBody.firstName());
        person.setLastName(requestBody.lastName());
        person.setDateOfBirth(requestBody.dateOfBirth());

        return personMapper.personNameWithIdDto(personRepository.save(person));
    }

    @Override
    public PersonDataDto updatePersonById(Integer id, PersonDataDto requestBody) {
        Person personFromRepo = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundByIdException(id));

        personFromRepo.setFirstName(requestBody.firstName());
        personFromRepo.setLastName(requestBody.lastName());
        personFromRepo.setDateOfBirth(requestBody.dateOfBirth());

        return personMapper.entityToPersonDataDto(personRepository.save(personFromRepo));
    }

    @Override
    public PersonNameWithIdDto getPersonById(Integer id) {
        Person person = findPersonFromRepoById(id);
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

    public Person findPersonFromRepoById(Integer id) {
        Optional<Person> personFromRepo = personRepository.findById(id);

        if (personFromRepo.isEmpty()) {
            throw new PersonNotFoundByIdException(id);
        }

        return personFromRepo.get();
    }


}
