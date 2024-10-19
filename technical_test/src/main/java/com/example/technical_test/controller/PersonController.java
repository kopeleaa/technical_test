package com.example.technical_test.controller;

import com.example.technical_test.dto.PersonRequestDto;
import com.example.technical_test.dto.PersonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/person")
public interface PersonController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PersonResponseDto createPerson(@RequestBody PersonRequestDto requestBody);
}
