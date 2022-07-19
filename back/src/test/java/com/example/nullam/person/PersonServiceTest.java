package com.example.nullam.person;

import com.example.nullam.exceptions.PersonWithThisPersonalIdCodeAlreadyExists;
import com.example.nullam.paymentmethod.PaymentMethod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonServiceTest {

    PersonService underTest;
    @Autowired
   private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        underTest = new PersonService(personRepository);
    }

    @AfterEach
    void tearDown() {
        personRepository.deleteAll();
    }

//    @Test
//    void showAllPersons() {
//        //given
//        Person mihkel = new Person(1L, "Mihkel", "Amman",
//                "37810014694", PaymentMethod.TRANSFER, "Student");
//
//        Person uku = new Person(2L, "Uku", "Leele",
//                "37810014944", PaymentMethod.CASH, "Sponsor");
//        personRepository.saveAll(Arrays.asList(mihkel, uku));
//
//        //when
//        boolean requestSuccessful = underTest.showAllPersons().size() == 2;
//
//        //then
//        assertTrue(requestSuccessful);
//    }
//
//    @Test
//    void savePerson() throws PersonWithThisPersonalIdCodeAlreadyExists {
//        //given
//        Person mihkel = new Person(1L, "Mihkel", "Amman",
//                "37810014694", PaymentMethod.TRANSFER, "Student");
//
//        //when
//        boolean requestSuccessful = underTest.savePerson(mihkel) != null;
//        //then
//
//        assertTrue(requestSuccessful);
//    }
//
//    @Test
//    void findPersonById() {
//        //given
//        Person mihkel = new Person(1L, "Mihkel", "Amman",
//                "37810014694", PaymentMethod.TRANSFER, "Student");
//        personRepository.save(mihkel);
//
//        //when
//        boolean requestSuccessful = underTest.findPersonById(mihkel.getId()) != null;
//        //then
//
//        assertTrue(requestSuccessful);
//    }
//
//    @Test
//    void updatePersonInfoByPersonId() {
//        //given
//        Person mihkel = new Person(1L, "Mihkel", "Amman",
//                "37810014694", PaymentMethod.TRANSFER, "Student");
//        personRepository.save(mihkel);
//
//        Person updatedPerson = new Person();
//        updatedPerson.setDescription("Test description");
//
//
//        //when
//        underTest.updatePersonInfoByPersonId(mihkel.getId(), updatedPerson);
//        boolean requestSuccessful = mihkel.getId().equals(1L) && mihkel.getFirstName().equals("Mihkel") && mihkel.getPersonalIdCode().equals("37810014694")
//                && mihkel.getPaymentMethod().equals(PaymentMethod.TRANSFER) && mihkel.getDescription().equals("Test description");
//        //then
//
//        assertTrue(requestSuccessful);
//
//    }
//
//    @Test
//    void deletePersonById() {
//    }
}