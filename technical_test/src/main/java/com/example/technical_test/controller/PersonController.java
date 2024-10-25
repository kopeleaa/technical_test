package com.example.technical_test.controller;

import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/person")
public interface PersonController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PersonNameWithIdDto createPerson(@Valid @RequestBody PersonDataDto requestBody);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    PersonDataDto updatePersonById(@RequestBody @Valid PersonDataDto requestBody, @PathVariable Integer id);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    PersonNameWithIdDto getPersonById(@PathVariable Integer id);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<PersonNameWithIdDto> getAllPersons();

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deletePerson(@PathVariable Integer id);
}
