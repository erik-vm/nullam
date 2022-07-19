package com.example.nullam.person;

import com.example.nullam.exceptions.PersonNotFound;
import com.example.nullam.exceptions.PersonWithThisPersonalIdCodeAlreadyExists;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    private final Pattern personalIdCodePattern = Pattern.compile("^([1-6]{1})([0-9]{2})([01]{1})([0-9]{1})([0-3]{1})([0-9]{1})([0-9]{4})$");


    List<Person> showAllPersons() throws Exception {
        if (personRepository.findAll().isEmpty()) {
            throw new Exception("No persons in database.");
        }
        return personRepository.findAll();
    }

    Person savePerson(Person person) throws Exception {
        if (!doesPersonExistByPersonalIdCode(person.getPersonalIdCode())) {
            throw new PersonWithThisPersonalIdCodeAlreadyExists(person.getPersonalIdCode());
        }
        validatePersonalIdCode(person.getPersonalIdCode());
        return personRepository.save(person);
    }

    public Person findPersonById(Long id) throws PersonNotFound {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isEmpty()) {
            throw new PersonNotFound(id);
        }
        return personOptional.get();
    }

    Person updatePersonInfoByPersonId(Long id, Person updatedPerson) throws Exception {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isEmpty()) {
            throw new PersonNotFound(id);
        }
        Person existingPerson = personOptional.get();
        if (updatedPerson.getPersonalIdCode() != null || updatedPerson.getId() != null) {
            throw new Exception("Changing values of id and personal id code is prohibited!");
        }
        if (updatedPerson.getFirstName() != null) {
            existingPerson.setFirstName(updatedPerson.getFirstName());
        }
        if (updatedPerson.getLastName() != null) {
            existingPerson.setLastName(updatedPerson.getLastName());
        }
        if (updatedPerson.getDescription() != null) {
            existingPerson.setDescription(updatedPerson.getDescription());
        }
        if (updatedPerson.getPaymentMethod() != null) {
            existingPerson.setPaymentMethod(updatedPerson.getPaymentMethod());
        }
        return personRepository.save(existingPerson);

    }

    public void updatePersonEventList(Long id, Person updatedPerson) throws Exception {
        Person existingPerson = findPersonById(id);
        if (!updatedPerson.getPersonalIdCode().equals(existingPerson.getPersonalIdCode()) && !updatedPerson.getId().equals(existingPerson.getId())) {
        throw new Exception("Changing values of id and personal id code is prohibited!");
        }
            personRepository.save(existingPerson);
    }

    void deletePersonById(Long id) throws PersonNotFound {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isEmpty()) {
            throw new PersonNotFound(id);
        }
        personRepository.delete(personOptional.get());
    }

    private void validatePersonalIdCode(String personalIdCode) throws Exception {
        if (!personalIdCodePattern.matcher(personalIdCode).matches()) {
            throw new Exception(String.format("%s is not valid personal id code", personalIdCode));
        }
    }

    private boolean doesPersonExistByPersonalIdCode(String personalIdCode) {
        List<Person> personList = new ArrayList<>();
        for (Person person : personRepository.findAll()) {
            if (person.getPersonalIdCode().equals(personalIdCode)) {
                personList.add(person);
            }
        }
        return personList.isEmpty();
    }


}
