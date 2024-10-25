package com.example.technical_test.ServiceImpl;

import com.example.technical_test.domain.ContactInformation;
import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.ContactInfoDto;
import com.example.technical_test.dto.mapper.ContactInformationMapper;
import com.example.technical_test.enums.ContactInformationType;
import com.example.technical_test.repository.ContactInformationRepository;
import com.example.technical_test.repository.PersonRepository;
import com.example.technical_test.service.impl.ContactInformationServiceImpl;
import com.example.technical_test.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ContactInformationServiceImplTest {

    @Mock
    private ContactInformationRepository contactInformationRepository;

    @Mock
    private ContactInformationMapper contactInformationMapper;

    @Mock
    private PersonServiceImpl personService;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private ContactInformationServiceImpl contactInformationService;

    private static final String PHONE_NUMBER = "06205678912";
    private static final String EMAIL = "test@test.com";


    @Test
    void givenNoContactInformationInDB_WhenCreateContactInformation_ThenContactInformationCreated() {
        ContactInfoDto contactInfoDto = returnContactInfoDto(PHONE_NUMBER);
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setType(ContactInformationType.PHONE_NUMBER);
        contactInformation.setContactInformationValue(PHONE_NUMBER);

        Person person = returnMockPerson("Agatha", "Christie");
        Mockito.when(contactInformationMapper.dtoToEntity(contactInfoDto)).thenReturn(contactInformation);

        person.addContactInformation(contactInformation);

        ArgumentCaptor<ContactInformation> argument = ArgumentCaptor.forClass(ContactInformation.class);
        verify(person).addContactInformation(argument.capture());
        assertEquals("06205678912", argument.getValue().getContactInformationValue());

        verify(personRepository, atLeast(1)).save(any());
        verify(contactInformationRepository, atLeast(1)).save(any());
    }


    private ContactInfoDto returnContactInfoDto(String value) {
        return new ContactInfoDto(value, 1);
    }

    private ContactInformation returnMockContactInformation(String value, String type) {
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setContactInformationValue(value);
        contactInformation.setType(ContactInformationType.valueOf(type));

        return contactInformation;
    }


    private Person returnMockPerson(String firstName, String lastname) {
        Person person = new Person();

        person.setFirstName(firstName);
        person.setLastName(lastname);
        person.setDateOfBirth(LocalDate.of(1890, 9, 15));
        person.setId(1);

        return person;
    }
}
