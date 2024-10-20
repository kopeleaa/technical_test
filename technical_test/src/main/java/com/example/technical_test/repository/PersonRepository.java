package com.example.technical_test.repository;

import com.example.technical_test.domain.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer> {

    @Query("SELECT p FROM Person p WHERE p.firstName=:firstName AND p.lastName=:lastName AND p.dateOfBirth=:dateOfBirth")
    Optional<Person> findByNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);
}
