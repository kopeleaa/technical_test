package com.example.technical_test.ServiceImpl;

import com.example.technical_test.domain.Address;
import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import com.example.technical_test.dto.mapper.PersonMapper;
import com.example.technical_test.enums.AddressType;
import com.example.technical_test.exception.NoEntriesFoundException;
import com.example.technical_test.exception.PersonAlreadyExistsException;
import com.example.technical_test.exception.PersonNotFoundByIdException;
import com.example.technical_test.repository.PersonRepository;
import com.example.technical_test.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void givenZeroPersonInDB_WhenCreatePerson_ThenPersonCreated() {
        PersonDataDto dto = returnMockPersonDataDto("Agatha", "Christie");
        PersonNameWithIdDto expectedDto = new PersonNameWithIdDto(1,
                "Agatha Christie");

        Mockito.when(personRepository.findByNameAndDateOfBirth(any(), any(), any()))
                .thenReturn(Optional.empty());

        Mockito.when(personMapper.personDataDtoToEntity(dto))
                .thenReturn(new Person());

        Mockito.when(personMapper.personNameWithIdDto(any()))
                .thenReturn(expectedDto);

        PersonNameWithIdDto person2 = personService.createPerson(dto);

        verify(personRepository, atLeast(1)).save(any());

        assertEquals(person2.name(), dto.firstName() + " " + dto.lastName());
    }

    @Test
    void givenPersonIsAlreadyInDB_WhenCreatePerson_ThenExceptionThrown() {
        PersonDataDto dto = returnMockPersonDataDto("Agatha", "Christie");
        Person person = returnMockPerson("Agatha", "Christie");

        assert dto != null;
        Mockito.when(personRepository.findByNameAndDateOfBirth(dto.firstName(), dto.lastName(), dto.dateOfBirth()))
                        .thenReturn(Optional.of(person));


        assertThrows(PersonAlreadyExistsException.class, () -> personService.createPerson(dto));
    }

    @Test
    void givenNoPersonInDB_WhenGetPersonById_ThenExceptionThrown() {
        Mockito.when(personRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(PersonNotFoundByIdException.class, () -> personService.getPersonById(1));
    }

    @Test
    void givenOnePersonInDB_WhenGetPersonById_ThenDtoReturned() {
        PersonNameWithIdDto dto = new PersonNameWithIdDto(1, "Ben Hur");
        Person person = returnMockPerson("Ben", "Hur");

        Mockito.when(personRepository.findById(1)).thenReturn(Optional.of(person));
        Mockito.when(personService.getPersonById(1)).thenReturn(dto);
        PersonNameWithIdDto personById = personService.getPersonById(1);

        assertEquals(dto.personId(), personById.personId());
        assertEquals(dto.name(), personById.name());
    }

    @Test
    void givenTwoPersonsInDb_WhenGetAllPersons_ThenListReturned() {
        //Given
        Person person = returnMockPerson("Agatha", "Christie");
        Person person2 = returnMockPerson("Frank", "Theodore");

        //When
        given(personRepository.findAll())
                .willReturn(List.of(person, person2));
        List<PersonNameWithIdDto> personList = personService.getAllPersons();

        //Then
        assertThat(personList).isNotNull();
        assertEquals(2, personList.size());
    }

    @Test
    void givenNoEntriesInDB_WhenGetAllPersons_ThenExceptionThrown() {
        List<Person> emptyList = Collections.emptyList();
        Mockito.when(personRepository.findAll()).thenReturn(emptyList);
        assertThrows(NoEntriesFoundException.class, () -> personService.getAllPersons());
    }

    @Test
    void givenNoPersonInDB_WhenDeletePerson_ThenExceptionThrown() {
        Mockito.when(personRepository.findById(1)).thenThrow(PersonNotFoundByIdException.class);
        assertThrows(PersonNotFoundByIdException.class, () -> personService.deletePerson(1));
    }

    @Test
    void givenOnePersonInDB_WhenDeletePerson_ThenPersonDeleted() {
        Person person = returnMockPerson("Agatha", "Christie");
        personRepository.delete(person);
        verify(personRepository, times(1)).delete(person);
        //argumentCaptor
    }


    private Person returnMockPerson(String firstName, String lastname) {
        Person person = new Person();

        person.setFirstName(firstName);
        person.setLastName(lastname);
        person.setDateOfBirth(LocalDate.of(1890, 9, 15));
        person.setId(1);

        return person;
    }

    private Address returnMockAddress(Person person, Integer id, AddressType type, String city) {
        Address address = new Address();
        address.setId(id);
        address.setAddressType(type);
        address.setCity(city);
        address.setStreet("Apple");
        address.setHouseNumber(12);
        address.setZipCode("0123");
        address.setPerson(person);

        return address;
    }

    private PersonDataDto returnMockPersonDataDto(String firstname, String lastName) {
        return null;
        /*new PersonDataDto(
                firstname,
                lastName,
                LocalDate.of(1890, 9, 15),
                1,
                2,
                "06203345678",
                "test@test.com");

         */
    }
}
