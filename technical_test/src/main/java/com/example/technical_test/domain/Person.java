package com.example.technical_test.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
//@NoArgsConstructor
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "firts_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "permanent_address")
    @ToString.Exclude
    private Address permanentAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "temporary_address")
    @ToString.Exclude
    private Address temporaryAddress;


    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    @ToString.Exclude
    private List<ContactInformation> contactInformationList;

    public Person() {
        this.contactInformationList = new ArrayList<>();
    }

    public void addContactInformation(ContactInformation contactInformation) {
        if (!this.contactInformationList.contains(contactInformation)) {
            this.contactInformationList.add(contactInformation);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(dateOfBirth, person.dateOfBirth) && Objects.equals(permanentAddress, person.permanentAddress) && Objects.equals(temporaryAddress, person.temporaryAddress) && Objects.equals(contactInformationList, person.contactInformationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, dateOfBirth, permanentAddress, temporaryAddress, contactInformationList);
    }
}
