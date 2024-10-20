package com.example.technical_test.ServiceImpl;

import com.example.technical_test.domain.Address;
import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import com.example.technical_test.dto.mapper.PersonMapper;
import com.example.technical_test.enums.AddressType;
import com.example.technical_test.exception.PersonAlreadyExistsException;
import com.example.technical_test.repository.PersonRepository;
import com.example.technical_test.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

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

        Mockito.when(personService.createPerson(dto)).thenReturn(expectedDto);
        PersonNameWithIdDto person2 = personService.createPerson(dto);

        assertEquals(person2.name(), dto.firstName() + " " + dto.lastName());
    }

    @Test
    void givenPersonIsAlreadyInDB_WhenCreatePerson_ThenExceptionThrown() {
        PersonDataDto dto = returnMockPersonDataDto("Agatha", "Christie");

        Mockito.when(personService.createPerson(dto)).thenThrow(PersonAlreadyExistsException.class);
        assertThrows(PersonAlreadyExistsException.class, () -> personService.createPerson(dto));
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
        return new PersonDataDto(
                firstname,
                lastName,
                LocalDate.of(1890, 9, 15),
                1,
                2,
                "06203345678",
                "test@test.com");
    }
}
