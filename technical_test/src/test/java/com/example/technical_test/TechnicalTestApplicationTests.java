package com.example.technical_test;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class TechnicalTestApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EntityManager entityManager;

    @Test
    void contextLoads() {
    }

    @Test
    void givenNoPersonInDB_WhenCreatePerson_ThenPersonCreated() throws Exception {
        String requestBody = """
                        {
                         "firstName":"Agatha",
                         "lastName":"Christie",
                         "dateOfBirth":"1890-09-15"
                        }
                """;

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.personId", is(1)))
                .andExpect(jsonPath("$.name", is("Agatha Christie")));
    }


}
