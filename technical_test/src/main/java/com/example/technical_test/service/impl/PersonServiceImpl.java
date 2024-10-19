package com.example.technical_test.service.impl;

import com.example.technical_test.repository.PersonRepository;
import com.example.technical_test.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

}
