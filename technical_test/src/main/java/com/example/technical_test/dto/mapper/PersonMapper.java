package com.example.technical_test.dto.mapper;

import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import com.example.technical_test.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapper {

    private final AddressService addressService;

    public PersonNameWithIdDto entityToDto(Person person) {
        return PersonNameWithIdDto.builder()
                .personId(person.getId())
                .name(person.getFirstName() + " " + person.getLastName())
                .build();
    }

    public Person dtoToEntity(PersonDataDto dto) {
        Person person = new Person();

        person.setFirstName(dto.firstName());
        person.setLastName(dto.lastName());
        person.setDateOfBirth(dto.dateOfBirth());
        person.setPermanentAddress(dto.permanentAddressId());
    }

    public PersonDataDto entityToPersonDataDto(Person person) {
        return PersonDataDto.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .dateOfBirth(person.getDateOfBirth())
                .build();
    }

}
