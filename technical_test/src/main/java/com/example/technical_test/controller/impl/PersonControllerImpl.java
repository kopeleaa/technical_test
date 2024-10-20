package com.example.technical_test.controller.impl;

import com.example.technical_test.controller.PersonController;
import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import com.example.technical_test.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonControllerImpl implements PersonController {

    private final PersonService personService;

    @Override
    public PersonNameWithIdDto createPerson(PersonDataDto requestBody) {
        return personService.createPerson(requestBody);
    }

    @Override
    public PersonDataDto updatePersonById(PersonDataDto requestBody, Integer id) {
        return personService.updatePersonById(requestBody, id);
    }

    @Override
    public PersonNameWithIdDto getPersonById(Integer id) {
        return personService.getPersonById(id);
    }
}
