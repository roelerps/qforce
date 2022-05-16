package nl.qnh.qforce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

/**
 * The domain class representing a Star Wars person following the QForce definitions.
 *
 * @author roelerps
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PersonQForce implements Person{

    private long id;
    private String name;
    private String birthYear;
    private Gender gender;
    private Integer height;
    private Integer weight;
    private List<Movie> movies;



    /**
     * Returns the unique id of the person.
     *
     * @return the unique id of the person
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Retuns the name of the person.
     *
     * @return the name of the person
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Retuns the birth year of the person.
     *
     * @return the birth year of the person
     */
    @Override
    public String getBirthYear() {
        return birthYear;
    }

    /**
     * Retuns the gender of the person.
     *
     * @return the gender of the person
     */
    @Override
    public Gender getGender() {
        return gender;
    }

    /**
     * Retuns the height of the person in centimeters.
     *
     * @return the height of the person
     */
    @Override
    public Integer getHeight() {
        return height;
    }

    /**
     * Retuns the weight of the person in kilograms.
     *
     * @return the weight of the person
     */
    @Override
    public Integer getWeight() {
        return weight;
    }

    /**
     * Returns the movies the person has been in.
     *
     * @return the movies the person has been in
     */
    @Override
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * Set the id of the person.
     *
     * @param id the id of the person
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Set the name of the person.
     *
     * @param name the name of the person
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the birth year of the person.
     *
     * @param birthYear the birth year of the person
     */
    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    /**
     * Set the gender of the person.
     *
     * @param gender the gender of the person
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Set the height of the person.
     *
     * @param height the heigth of the person
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * Set the weight of the person.
     *
     * @param weight the weight of the person
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * Set the movies the person acted in.
     *
     * @param movies the movies where the person acted in
     */
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "PersonQForce{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear='" + birthYear + '\'' +
                ", gender=" + gender +
                ", height=" + height +
                ", weight=" + weight +
                ", movies=" + movies +
                '}';
    }
}
