package com.example.technical_test.dto.mapper;

import com.example.technical_test.domain.Address;
import com.example.technical_test.domain.ContactInformation;
import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import com.example.technical_test.enums.AddressType;
import com.example.technical_test.enums.ContactInformationType;
import com.example.technical_test.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        Address permanentAddress = addressService.getAddressById(dto.permanentAddressId());
        permanentAddress.setAddressType(AddressType.PERMANENT);
        Address temporaryAddress = addressService.getAddressById(dto.permanentAddressId());
        temporaryAddress.setAddressType(AddressType.TEMPORARY);

        List<Address> addresses = new ArrayList<>();
        addresses.add(permanentAddress);
        addresses.add(temporaryAddress);

        ContactInformation phoneNumber = new ContactInformation();
        phoneNumber.setContactInformationValue(dto.phoneNumber());
        phoneNumber.setType(ContactInformationType.PHONE_NUMBER);
        ContactInformation email = new ContactInformation();
        email.setContactInformationValue(dto.email());
        email.setType(ContactInformationType.EMAIL_ADDRESS);

        List<ContactInformation> contactInformationList = person.getContactInformationList();
        contactInformationList.add(phoneNumber);
        contactInformationList.add(email);

        person.setFirstName(dto.firstName());
        person.setLastName(dto.lastName());
        person.setDateOfBirth(dto.dateOfBirth());
        person.setAddresses(addresses);
        person.setContactInformationList(contactInformationList);

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
