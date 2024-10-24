package com.example.technical_test.ServiceImpl;

import com.example.technical_test.domain.ContactInformation;
import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.ContactInfoDto;
import com.example.technical_test.dto.mapper.ContactInformationMapper;
import com.example.technical_test.enums.ContactInformationType;
import com.example.technical_test.repository.ContactInformationRepository;
import com.example.technical_test.repository.PersonRepository;
import com.example.technical_test.service.ContactInformationService;
import com.example.technical_test.service.PersonService;
import com.example.technical_test.service.impl.ContactInformationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactInformationServiceImplTest {

    @Mock
    private ContactInformationRepository contactInformationRepository;

    @Mock
    private ContactInformationMapper contactInformationMapper;

    @Mock
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private ContactInformationServiceImpl contactInformationService;

    private static final String PHONE_NUMBER = "06205678912";
    private static final String EMAIL = "test@test.com";




    @Test
    void givenNoContactInformationInDB_WhenCreateContactInformation_ThenContactInformationCreated() {
        ContactInfoDto contactInfoDto = returnMockContactInfoDto(PHONE_NUMBER);
        ContactInformation contactInformation = returnMockContactInformation(PHONE_NUMBER, ContactInformationType.PHONE_NUMBER.name());
       // Person person = returnMockPerson("Agatha", "Christie");

        Mockito.when(contactInformationMapper.dtoToEntity(any())).thenReturn(contactInformation);
        Person person = contactInformation.getPerson();
        person.setContactInformationList(List.of(contactInformation));
        Mockito.when(personRepository.save(any())).thenReturn(person);
        Mockito.when(contactInformationRepository.save(any())).thenReturn(contactInformation);
        verify(personRepository, atLeast(1)).save(any());
        contactInformationService.createContactInformation(contactInfoDto);

        verify(personRepository, atLeast(1)).save(any());
        verify(contactInformationRepository, atLeast(1)).save(any());
    }


    private ContactInfoDto returnMockContactInfoDto(String value) {
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
