package com.example.technical_test.controller;

import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import com.example.technical_test.dto.PersonUpdateDto;
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
    PersonDataDto updatePersonById(@RequestBody @Valid PersonUpdateDto requestBody, @PathVariable Integer id);

    @GetMapping("/{id}")
    PersonNameWithIdDto getPersonById(@PathVariable Integer id);

    @GetMapping
    List<PersonNameWithIdDto> getAllPersons();

    @DeleteMapping("/{id}")
    void deletePerson(@PathVariable Integer id);
}
