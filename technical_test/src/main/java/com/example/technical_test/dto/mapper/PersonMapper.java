package com.example.technical_test.dto.mapper;

import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.PersonRequestDto;
import com.example.technical_test.dto.PersonResponseDto;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public PersonResponseDto entityToDto(Person person) {
        return PersonResponseDto.builder()
                .personId(person.getId())
                .name(person.getFirstName() + " " + person.getLastName())
                .build();
    }

    public Person dtoToEntity(PersonRequestDto dto) {
        Person person = new Person();

        person.setFirstName(dto.firstName());
        person.setLastName(dto.lastName());

        return person;
    }

}
