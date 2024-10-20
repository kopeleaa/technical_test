package com.example.technical_test.ServiceImpl;

import com.example.technical_test.domain.Person;
import com.example.technical_test.dto.PersonDataDto;
import com.example.technical_test.dto.PersonNameWithIdDto;
import com.example.technical_test.dto.mapper.PersonMapper;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;


    @Test
    void givenZeroPersonInDb_WhenCreatePerson_ThenPersonCreated() {
        Person person = returnMockPerson();
        PersonDataDto dto = new PersonDataDto("Agatha", "Christie",
                LocalDate.of(1890, 9, 15));

        PersonNameWithIdDto expectedDto = new PersonNameWithIdDto(1,
                "Agatha Christie");


        given(personService.createPerson(dto)).willReturn(expectedDto);
        PersonNameWithIdDto person1 = personService.createPerson(dto);

        when(personRepository.findById(1)).thenReturn(Optional.of(person));
        PersonNameWithIdDto personById = personService.getPersonById(1);


        assertEquals(personById.name(), person1.name());
        assertEquals(personById.personId(), person1.personId());
    }

    @Test
    void getAllPerson() {
        //given
        Person person = new Person();
        person.setFirstName("Ahnis");
        person.setLastName("Gotham");
        person.setDateOfBirth(LocalDate.of(2000, 10, 10));
        Person person2 = new Person();
        person2.setFirstName("Saksham");
        person2.setLastName("New york");
        person2.setDateOfBirth(LocalDate.of(2001, 11, 11));

        //When
        given(personRepository.findAll())
                .willReturn(List.of(person, person2));
        var personList = personService.getAllPersons();
        //Then

        assertThat(personList).isNotNull();
        assertEquals(personList.size(), 2);
    }


    private Person returnMockPerson() {
        Person person = new Person();

        person.setFirstName("Agatha");
        person.setLastName("Christie");
        person.setDateOfBirth(LocalDate.of(1890, 9, 15));
        person.setId(1);

        return person;
    }
}
