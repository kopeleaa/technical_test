package com.example.technical_test.controller.impl;

import com.example.technical_test.controller.ContactInformationController;
import com.example.technical_test.service.ContactInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ContactInformationControllerImpl implements ContactInformationController {

    private final ContactInformationService contactInformationService;

}
