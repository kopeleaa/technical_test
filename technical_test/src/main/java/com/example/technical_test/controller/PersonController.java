package com.example.technical_test.controller;

import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/person")
public interface PersonController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PersonNameWithIdDto createPerson(@RequestBody PersonDataDto requestBody);

    @PutMapping("/{id}")
    PersonDataDto updatePersonById(@RequestBody PersonDataDto requestBody, @PathVariable Integer id);

    @GetMapping("/{id}")
    PersonNameWithIdDto getPersonById(@PathVariable Integer id);

    @GetMapping
    List<PersonNameWithIdDto> getAllPersons();

    @DeleteMapping("/{id}")
    void deletePerson(@PathVariable Integer id);
}
