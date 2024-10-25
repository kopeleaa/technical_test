package com.example.technical_test.dto.mapper;

import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapper {


    public PersonNameWithIdDto entityToPersonNameWithIdDto(Person person) {
        return PersonNameWithIdDto.builder()
                .personId(person.getId())
                .name(person.getFirstName() + " " + person.getLastName())
                .build();
    }

    public Person personDataDtoToEntity(PersonDataDto dto) {
        Person person = new Person();
        person.setFirstName(dto.firstName());
        person.setLastName(dto.lastName());
        person.setDateOfBirth(dto.dateOfBirth());

        return person;
    }

    public PersonDataDto entityToPersonDataDto(Person person) {
        return PersonDataDto.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .dateOfBirth(person.getDateOfBirth())
                .build();
    }



}
