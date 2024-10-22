package com.example.technical_test.controller.impl;

import com.example.technical_test.controller.PersonController;
import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import com.example.technical_test.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonControllerImpl implements PersonController {

    private final PersonService personService;

    @Override
    public PersonNameWithIdDto createPerson(PersonDataDto requestBody) {
        log.info("HTTP request received: POST /person {}", requestBody);
        return personService.createPerson(requestBody);
    }

    @Override
    public PersonDataDto updatePersonById(@Valid PersonDataDto requestBody, Integer id) {
        log.info("HTTP request received: PUT /person/{} , {}", id, requestBody);
        return personService.updatePersonById(id, requestBody);
    }

    @Override
    public PersonNameWithIdDto getPersonById(Integer id) {
        log.info("HTTP request received: GET /person/{}", id);
        return personService.getPersonById(id);
    }

    @Override
    public List<PersonNameWithIdDto> getAllPersons() {
        log.info("HTTP request received: GET /person");
        return personService.getAllPersons();
    }

    @Override
    public void deletePerson(Integer id) {
        log.info("HTTP request received: DELETE /person/{}", id);
        personService.deletePerson(id);
    }
}
