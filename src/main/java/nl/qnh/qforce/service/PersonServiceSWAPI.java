package nl.qnh.qforce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.qnh.qforce.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The person service to search and retrieve persons from the SWAPI
 *
 * @author roelerps
 */
@Service
public class PersonServiceSWAPI implements PersonService{

    private static final String SWAPI_URL = "https://swapi.dev/api/people";

    public PersonServiceSWAPI() {
    }


    /**
     * Searches for persons.
     *
     * @param query the query string
     * @return the list of persons
     */
    @Override
    public List<Person> search(String query) {
        // Request the data from SWAPI via HttpRequest
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(SWAPI_URL + "/?search=" + query))  ///people/?search=r2
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Try to map the data from SWAPI to the object PersonSWAPI
        List<PersonSWAPI> personSWAPIList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            personSWAPIList = mapper.readValue(response.body().substring(response.body().indexOf("results") + 9, response.body().length() -1 ), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Convert the SWAPI data to QForce data
        List<Person> personQForceList= new ArrayList<>();
        for (PersonSWAPI personSWAPI : personSWAPIList){
            if (personSWAPI.getName() != null){
                PersonQForce personQForce = convert(personSWAPI);
                List<Movie> movies = new ArrayList<>();
                for (String movieURL:personSWAPI.getFilms()) {
                    movies.add(convert(getMovie(movieURL)));
                }
                personQForce.setMovies(movies);
                personQForceList.add(personQForce);
            }
        }
        return personQForceList;
    }

    /**
     * Returns the person with the provided id.
     *
     * @param id the id of the person
     * @return the person
     */
    @Override
    public Optional<Person> get(long id) {
        // Request the data from SWAPI via HttpRequest
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(SWAPI_URL + "/" + id + "/"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Try to map the data from SWAPI to the object PersonSWAPI
        PersonSWAPI personSWAPI = null;
        PersonQForce personQForce = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            personSWAPI = mapper.readValue(response.body(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Convert the SWAPI data to QForce data
        if (personSWAPI.getName() != null){
            personQForce = convert(personSWAPI);
            List<Movie> movies = new ArrayList<>();
            for (String movieURL:personSWAPI.getFilms()) {
                movies.add(convert(getMovie(movieURL)));
            }
            personQForce.setMovies(movies);
        }
        else{
            return Optional.empty();
        }
        return Optional.of(personQForce);
    }

    /**
     * Converts the person from SWAPI to QForce format.
     *
     * @param personSwapi the person in PersonSWAPI format
     * @return the person in PersonQForce format
     */
    private PersonQForce convert(PersonSWAPI personSwapi){
        PersonQForce personQforce = new PersonQForce();

        // Get id from url and set id
        String id = personSwapi.getUrl().substring(personSwapi.getUrl().indexOf("people")+7,personSwapi.getUrl().length()-1);
        if(id != null && id.matches("[0-9.]+")){
            personQforce.setId(Integer.parseInt(id));
        }
        else personQforce.setId(-1);
        // Convert name/birthyear/gender/height/weight of person
        personQforce.setName(personSwapi.getName());
        personQforce.setBirthYear(personSwapi.getBirthYear());
        personQforce.setGender(personSwapi.getGender());
        personQforce.setHeight(personSwapi.getHeight());
        personQforce.setWeight(personSwapi.getWeight());

        return personQforce;
    }

    /**
     * Returns the movie.
     *
     * @param movieURL the url of the movie
     * @return the movie
     */
    private MovieSWAPI getMovie(String movieURL){
        // Request the data from SWAPI via HttpRequest
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(movieURL))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Try to map the data from SWAPI to the object MovieSWAPI
        MovieSWAPI movie = null;
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        try {
            movie = mapper.readValue(response.body(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return movie;
    }


    /**
     * Converts the movie from SWAPI to QForce format.
     *
     * @param movieSWAPI the movie in MovieSWAPI format
     * @return the movie in MovieQForce format
     */
    private MovieQForce convert(MovieSWAPI movieSWAPI){
        MovieQForce movieQforce = new MovieQForce();

        // Convert title/episode/director/releasedate of movie
        movieQforce.setTitle(movieSWAPI.getTitle());
        movieQforce.setEpisode(movieSWAPI.getEpisode());
        movieQforce.setDirector(movieSWAPI.getDirector());
        movieQforce.setReleaseDate(movieSWAPI.getReleaseDate());

        return movieQforce;
    }
}
