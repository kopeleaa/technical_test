package com.example.technical_test.ServiceImpl;

import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import com.example.technical_test.dto.mapper.PersonMapper;
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

        Mockito.when(personRepository.findByNameAndDateOfBirth(any(), any(), any())).thenReturn(Optional.empty());
        Mockito.when(personMapper.personDataDtoToEntity(dto)).thenReturn(new Person());
        Mockito.when(personMapper.entityToPersonNameWithIdDto(any())).thenReturn(expectedDto);

        PersonNameWithIdDto person2 = personService.createPerson(dto);

        verify(personRepository, atLeast(1)).save(any());

        assertEquals(person2.name(), dto.firstName() + " " + dto.lastName());
    }

    @Test
    void givenPersonIsAlreadyInDB_WhenCreatePerson_ThenExceptionThrown() {
        PersonDataDto dto = returnMockPersonDataDto("Agatha", "Christie");
        Person person = returnMockPerson("Agatha", "Christie");

        Mockito.when(personRepository.findByNameAndDateOfBirth(any(), any(), any()))
                .thenReturn(Optional.of(person));
        Person person1 = personRepository.findByNameAndDateOfBirth(dto.firstName(), dto.lastName(), dto.dateOfBirth()).get();

        assertEquals(person1, person);
        assertThrows(PersonAlreadyExistsException.class, () -> personService.createPerson(dto));
    }

    @Test
    void givenNoPersonInDB_WhenUpdatePersonById_ThenExceptionThrown() {
        PersonDataDto dto = returnMockPersonDataDto("Agatha", "Christie");

        Mockito.when(personRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(PersonNotFoundByIdException.class, ()-> personService.updatePersonById(1, dto));
        verify(personRepository, atLeast(1)).findById(any());
    }

    @Test
    void givenOnePersonInDB_WhenUpdatePersonById_ThenUpdatePerson() {
        PersonDataDto dto = returnMockPersonDataDto("Agatha", "Christie");
        Person person = returnMockPerson("Agatha", "Christie");

        Mockito.when(personRepository.findById(any())).thenReturn(Optional.of(person));
        personService.updatePersonById(1, dto);
        verify(personRepository, atLeast(1)).save(person);
    }


    @Test
    void givenNoPersonInDB_WhenGetPersonById_ThenExceptionThrown() {
        Mockito.when(personRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(PersonNotFoundByIdException.class, () -> personService.findPersonFromRepoById(1));
        assertThrows(PersonNotFoundByIdException.class, () -> personService.getPersonById(1));

        verify(personRepository, atLeast(1)).findById(any());
    }

    @Test
    void givenOnePersonInDB_WhenGetPersonById_ThenDtoReturned() {
        PersonNameWithIdDto dto = new PersonNameWithIdDto(1, "Ben Hur");
        Person person = returnMockPerson("Ben", "Hur");

        Mockito.when(personRepository.findById(any())).thenReturn(Optional.of(person));
        Mockito.when(personMapper.entityToPersonNameWithIdDto(person)).thenReturn(dto);
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
        Mockito.when(personRepository.findAll()).thenReturn(List.of(person, person2));
        List<PersonNameWithIdDto> personList = personService.getAllPersons();

        //Then
        assertThat(personList).isNotNull();
        assertEquals(2, personList.size());
    }

    @Test
    void givenNoEntriesInDB_WhenGetAllPersons_ThenExceptionThrown() {
        List<Person> emptyList = Collections.emptyList();
        Mockito.when(personRepository.findAll()).thenReturn(emptyList);
        assertEquals(0, personRepository.findAll().size());
        assertThrows(NoEntriesFoundException.class, () -> personService.getAllPersons());
    }

    @Test
    void givenNoPersonInDB_WhenDeletePerson_ThenExceptionThrown() {
        Mockito.when(personRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(PersonNotFoundByIdException.class, () -> personService.deletePerson(1));
    }

    @Test
    void givenOnePersonInDB_WhenDeletePerson_ThenPersonDeleted() {
        Person person = returnMockPerson("Agatha", "Christie");
        Mockito.when(personRepository.findById(any())).thenReturn(Optional.of(person));
        personService.deletePerson(any());
        verify(personRepository, times(1)).delete(person);
    }


    private Person returnMockPerson(String firstName, String lastname) {
        Person person = new Person();

        person.setFirstName(firstName);
        person.setLastName(lastname);
        person.setDateOfBirth(LocalDate.of(1890, 9, 15));
        person.setId(1);

        return person;
    }


    private PersonDataDto returnMockPersonDataDto(String firstname, String lastName) {
        return
                new PersonDataDto(
                        firstname,
                        lastName,
                        LocalDate.of(1890, 9, 15));

    }
}
