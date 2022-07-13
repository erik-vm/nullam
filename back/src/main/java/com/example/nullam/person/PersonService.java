package com.example.nullam.person;

import com.example.nullam.company.Company;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    private final Pattern personalIdCodePattern = Pattern.compile("^([1-6]{1})([0-9]{2})([01]{1})([0-9]{1})([0-3]{1})([0-9]{1})([0-9]{4})$");


    List<Person> showAllPersons() {
        if (personRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return personRepository.findAll();
    }

    public Person savePerson(Person person) {
        if (doesPersonExistByPersonalIdCode(person.getPersonalIdCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        validatePersonalIdCode(person.getPersonalIdCode());
        return personRepository.save(person);
    }

    public Person findPersonById(Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return personOptional.get();
    }

    Person updatePersonInfoByCompanyId(Long id, Person updatedPerson) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Person existingPerson = personOptional.get();
        existingPerson.setFirstName(updatedPerson.getFirstName());
        existingPerson.setLastName(updatedPerson.getLastName());
        existingPerson.setDescription(updatedPerson.getDescription());
        existingPerson.setPaymentMethod(updatedPerson.getPaymentMethod());

        return personRepository.save(existingPerson);
    }

    void deletePersonById(Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        personRepository.delete(personOptional.get());
    }

    private void validatePersonalIdCode(String personalIdCode) {
        if (!personalIdCodePattern.matcher(personalIdCode).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
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
