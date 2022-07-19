package com.example.nullam.person;

import com.example.nullam.exceptions.PersonNotFound;
import com.example.nullam.exceptions.PersonWithThisPersonalIdCodeAlreadyExists;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping
    List<Person> showAllPersons() throws Exception {
        return personService.showAllPersons();
    }

    @GetMapping("/find={id}")
    Person findPersonById(@PathVariable("id") Long id) throws PersonNotFound {
        return personService.findPersonById(id);
    }

    @PostMapping("/save")
    Person savePerson(@RequestBody Person person) throws Exception {
       return personService.savePerson(person);
    }

    @PutMapping("update={id}")
    Person updateCompanyById(@PathVariable("id") Long id, @RequestBody Person person) throws Exception {
        return personService.updatePersonInfoByPersonId(id, person);
    }
    @DeleteMapping("/delete={id}")
    void deletePersonById(@PathVariable("id") Long id) throws PersonNotFound {
        personService.deletePersonById(id);
    }
}
