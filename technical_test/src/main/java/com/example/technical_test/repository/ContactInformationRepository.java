package com.example.technical_test.repository;

import com.example.technical_test.domain.ContactInformation;
import org.springframework.data.repository.CrudRepository;

public interface ContactInformationRepository extends CrudRepository<ContactInformation, Integer> {
}
