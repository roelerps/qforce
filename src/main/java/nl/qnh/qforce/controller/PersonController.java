package nl.qnh.qforce.controller;

import nl.qnh.qforce.domain.Person;
import nl.qnh.qforce.service.PersonService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * The person controller acts as a REST controller in order to search persons by query or get person by id
 *
 * @author roelerps
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Searches for persons.
     *
     * @param query the query string
     * @return the list of persons
     */
    @GetMapping
    public List<Person> searchQuery(@RequestParam("q") String query){
        return personService.search(query);
    }

    /**
     * Returns the person with the provided id.
     *
     * @param id the id of the person
     * @return the person
     */
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
