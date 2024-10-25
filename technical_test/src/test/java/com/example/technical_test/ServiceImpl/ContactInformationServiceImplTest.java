package com.example.technical_test.ServiceImpl;

import com.example.technical_test.domain.ContactInformation;
import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.ContactInfoDto;
import com.example.technical_test.dto.mapper.ContactInformationMapper;
import com.example.technical_test.enums.ContactInformationType;
import com.example.technical_test.exception.ContactInformationAlreadyExistsException;
import com.example.technical_test.repository.ContactInformationRepository;
import com.example.technical_test.repository.PersonRepository;
import com.example.technical_test.service.impl.ContactInformationServiceImpl;
import com.example.technical_test.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactInformationServiceImplTest {

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

    private static final String PHONE_NUMBER_VALUE = "06205678912";


    @Test
    void givenNoContactInformationInDB_WhenCreateContactInformation_ThenContactInformationCreated() {
        ContactInfoDto contactInfoDto = returnContactInfoDto(PHONE_NUMBER_VALUE);
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setType(ContactInformationType.PHONE_NUMBER);
        contactInformation.setContactInformationValue(PHONE_NUMBER_VALUE);

        Person person = returnPerson("Agatha", "Christie");
        contactInformation.setPerson(person);
        when(contactInformationMapper.dtoToEntity(contactInfoDto)).thenReturn(contactInformation);

        person.addContactInformation(contactInformation);
        contactInformationService.createContactInformation(contactInfoDto);

        ArgumentCaptor<ContactInformation> ctiCaptor = ArgumentCaptor.forClass(ContactInformation.class);
        verify(contactInformationRepository).save(ctiCaptor.capture());
        ContactInformation contactInformation1 = ctiCaptor.getValue();
        assertEquals(person, contactInformation1.getPerson());
        assertEquals(contactInformation.getType(), contactInformation1.getType());
        assertEquals(contactInformation.getContactInformationValue(),
                contactInformation1.getContactInformationValue());


        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(personService).savePerson(argument.capture());
        Person person1 = argument.getValue();
        assertEquals(person.getContactInformationList(), person1.getContactInformationList());

        verify(personService, atLeast(1)).savePerson(any());
        verify(contactInformationRepository, atLeast(1)).save(any());
    }

    @Test
    void givenContactInformationAlreadyExists_WhenCreateContactInformation_ThenExceptionThrown() {
        ContactInfoDto dto = returnContactInfoDto(PHONE_NUMBER_VALUE);
        ContactInformation cti = new ContactInformation();
        cti.setType(ContactInformationType.PHONE_NUMBER);
        cti.setContactInformationValue(PHONE_NUMBER_VALUE);
        Person person = returnPerson("Agatha", "Christie");
        cti.setPerson(person);
        contactInformationRepository.save(cti);
        when(contactInformationMapper.dtoToEntity(dto)).thenReturn(cti);
        when(contactInformationRepository.save(cti)).thenThrow(DataIntegrityViolationException.class);

        assertThrows(ContactInformationAlreadyExistsException.class,
                () -> contactInformationService.createContactInformation(dto));
    }


    private ContactInfoDto returnContactInfoDto(String value) {
        return new ContactInfoDto(value, 1);
    }


    private Person returnPerson(String firstName, String lastname) {
        Person person = new Person();

        person.setFirstName(firstName);
        person.setLastName(lastname);
        person.setDateOfBirth(LocalDate.of(1890, 9, 15));

        return person;
    }
}
