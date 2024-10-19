package com.example.technical_test.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contact_information")
@NoArgsConstructor
@Data
public class ContactInformation {
}
