package com.example.technical_test.repository;

import com.example.technical_test.domain.ContactInformation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ContactInformationRepository extends CrudRepository<ContactInformation, Integer> {

   @Query("SELECT c FROM ContactInformation c WHERE c.contactInformationValue=:value")
    Optional<ContactInformation> getContactInformationByValue(String value);
}
