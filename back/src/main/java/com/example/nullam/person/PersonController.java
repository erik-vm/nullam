package com.example.nullam.person;

import com.example.nullam.company.Company;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping
    List<Person> showAllPersons(){
        return personService.showAllPersons();
    }

    @GetMapping("/find={id}")
    Person findPersonById(@PathVariable("id") Long id){
        return personService.findPersonById(id);
    }

    @PostMapping("/save")
    Person savePerson(@RequestBody Person person){
       return personService.savePerson(person);
    }

    @PutMapping("update={id}")
    Person updateCompanyById(@PathVariable("id") Long id, @RequestBody Person person){
        return personService.updatePersonInfoByCompanyId(id, person);
    }
    @DeleteMapping("/delete={id}")
    void deletePersonById(@PathVariable("id") Long id){
        personService.deletePersonById(id);
    }
}
