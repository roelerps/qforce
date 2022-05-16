package nl.qnh.qforce.controller;

import nl.qnh.qforce.domain.Person;
import nl.qnh.qforce.service.PersonService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> searchQuery(@RequestParam("q") String query){
        return personService.search(query);
    }

    @GetMapping("/{id}")
    public Optional<Person> getPersonById(@PathVariable long id){
        if (personService.get(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
        else {
            return personService.get(id);
        }

    }
}
