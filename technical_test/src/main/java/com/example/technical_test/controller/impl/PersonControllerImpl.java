package com.example.technical_test.controller.impl;

import com.example.technical_test.controller.PersonController;
import com.example.technical_test.dto.PersonRequestDto;
import com.example.technical_test.dto.PersonResponseDto;
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
    public PersonResponseDto createPerson(PersonRequestDto requestBody) {
        return null;
    }
}
