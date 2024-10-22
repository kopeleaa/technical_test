package com.example.technical_test.service;

import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import com.example.technical_test.dto.PersonUpdateDto;

import java.util.List;

public interface PersonService {
    PersonNameWithIdDto createPerson(PersonDataDto requestBody);

    PersonDataDto updatePersonById(PersonUpdateDto requestBody, Integer id);

    PersonNameWithIdDto getPersonById(Integer id);

    List<PersonNameWithIdDto> getAllPersons();

    void deletePerson(Integer id);

    void savePerson(Person person);
}
