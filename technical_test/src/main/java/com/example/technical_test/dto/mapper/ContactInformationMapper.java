package com.example.technical_test.dto.mapper;

import com.example.technical_test.domain.ContactInformation;
import com.example.technical_test.dto.ContactInfoDto;
import com.example.technical_test.enums.ContactInformationType;
import com.example.technical_test.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactInformationMapper {

    private final PersonService personService;

    public ContactInformation dtoToEntity(ContactInfoDto dto) {
        ContactInformation contactInformation = new ContactInformation();

        if (dto.value().contains("@")) {
            contactInformation.setType(ContactInformationType.EMAIL_ADDRESS);
        } else {
            contactInformation.setType(ContactInformationType.PHONE_NUMBER);
        }

        contactInformation.setContactInformationValue(dto.value());
        contactInformation.setPerson(personService.findPersonFromRepoById(dto.personId()));

        return contactInformation;
    }

    public ContactInfoDto entityToDto(ContactInformation contactInformation) {
        return ContactInfoDto.builder()
                .value(contactInformation.getContactInformationValue())
                .personId(contactInformation.getPerson().getId())
                .build();
    }
}
