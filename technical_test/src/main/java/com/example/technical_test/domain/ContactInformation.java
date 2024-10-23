package com.example.technical_test.domain;

import com.example.technical_test.enums.ContactInformationType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "contact_information")
@NoArgsConstructor
@Data
public class ContactInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "contact_information_value")
    private String contactInformationValue;

    @Enumerated(EnumType.STRING)
    private ContactInformationType type;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    @JsonBackReference
    private Person person;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactInformation that = (ContactInformation) o;
        return Objects.equals(contactInformationValue, that.contactInformationValue) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactInformationValue, type);
    }
}
