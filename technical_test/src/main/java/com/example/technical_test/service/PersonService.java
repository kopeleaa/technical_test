package com.example.technical_test.service;

import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;

public interface PersonService {
    PersonNameWithIdDto createPerson(PersonDataDto requestBody);

    PersonDataDto updatePersonById(PersonDataDto requestBody, Integer id);

    PersonNameWithIdDto getPersonById(Integer id);
}
