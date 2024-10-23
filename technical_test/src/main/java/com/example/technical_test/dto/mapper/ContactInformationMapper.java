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

        contactInformation.setContactInformationValue(dto.value());
        contactInformation.setType(ContactInformationType.valueOf(dto.type()));
        contactInformation.setPerson(personService.findPersonFromRepoById(dto.personId()));

        return contactInformation;
    }

    public ContactInfoDto entityToDto(ContactInformation contactInformation) {
        return ContactInfoDto.builder()
                .value(contactInformation.getContactInformationValue())
                .type(contactInformation.getType().name())
                .personId(contactInformation.getPerson().getId())
                .build();
    }
}
