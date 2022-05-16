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

        System.out.println(response.body());
        //System.out.println(response.body().indexOf("results"));
        System.out.println(response.body().substring(response.body().indexOf("results") + 9, response.body().length() -1 ));

        List<PersonSWAPI> personSWAPIList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            personSWAPIList = mapper.readValue(response.body().substring(response.body().indexOf("results") + 9, response.body().length() -1 ), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //System.out.println(personSWAPIList);

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


        PersonSWAPI personSWAPI = null;
        PersonQForce personQForce = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            personSWAPI = mapper.readValue(response.body(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (personSWAPI.getName() != null){
            personQForce = convert(personSWAPI);
            List<Movie> movies = new ArrayList<>();
            for (String movieURL:personSWAPI.getFilms()) {
                movies.add(convert(getMovie(movieURL)));
            }
            //personQForce.setId(id);
            personQForce.setMovies(movies);
        }
        else{
            return Optional.empty();
        }
        return Optional.of(personQForce);
    }

    private PersonQForce convert(PersonSWAPI personSwapi){
        PersonQForce personQforce = new PersonQForce();

        String id = personSwapi.getUrl().substring(personSwapi.getUrl().indexOf("people")+7,personSwapi.getUrl().length()-1);
        if(id != null && id.matches("[0-9.]+")){
            personQforce.setId(Integer.parseInt(id));
        }
        else personQforce.setId(-1);
        personQforce.setName(personSwapi.getName());
        personQforce.setBirthYear(personSwapi.getBirthYear());
        personQforce.setGender(personSwapi.getGender());
        personQforce.setHeight(personSwapi.getHeight());
        personQforce.setWeight(personSwapi.getWeight());

        return personQforce;
    }

    private MovieSWAPI getMovie(String movieURL){
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


        MovieSWAPI movie = null;
        //ObjectMapper mapper = new ObjectMapper();

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

    private MovieQForce convert(MovieSWAPI movieSWAPI){
        MovieQForce movieQforce = new MovieQForce();

        movieQforce.setTitle(movieSWAPI.getTitle());
        movieQforce.setEpisode(movieSWAPI.getEpisode());
        movieQforce.setDirector(movieSWAPI.getDirector());
        movieQforce.setReleaseDate(movieSWAPI.getReleaseDate());

        return movieQforce;
    }
}
