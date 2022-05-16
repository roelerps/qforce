package nl.qnh.qforce.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonSWAPI implements Person{

    private long id;
    private String name;
    private String birth_year;
    private String gender;
    private Gender genderEnum;
    private Integer height;
    private Integer mass;
    private List<String> films;
    private String url;


 /*   public PersonSWAPI(long id,  String name,  String birthYear,  Gender gender,  Integer height,  Integer weight,  List<String> filmURLs) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.filmURLs = filmURLs;
    }*/
    @JsonCreator
    public PersonSWAPI() {
        ;
    }

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
     * Returns the name of the person.
     *
     * @return the name of the person
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the birth year of the person.
     *
     * @return the birth year of the person
     */
    @Override
    public String getBirthYear() {
        return birth_year;
    }

    /**
     * Returns the gender of the person.
     *
     * @return the gender of the person
     */
    @Override
    public Gender getGender() {
        return genderEnum;
    }

    /**
     * Returns the height of the person in centimeters.
     *
     * @return the height of the person
     */
    @Override
    public Integer getHeight() {
        return height;
    }

    /**
     * Returns the weight of the person in kilograms.
     *
     * @return the weight of the person
     */
    @Override
    public Integer getWeight() {
        return mass;
    }

    /**
     * Returns the movies the person has been in.
     *
     * @return the movies the person has been in
     */
    @Override
    public List<Movie> getMovies() {
        return null;
    }

    public List<String> getFilms(){
        return films;
    }

    public String getUrl(){
        return url;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public void setGender(String gender) {
        this.gender = gender;
        switch (gender){
            case "male":
                genderEnum = Gender.MALE;
                break;
            case "female":
                genderEnum = Gender.FEMALE;
                break;
            case "n/a":
                genderEnum = Gender.NOT_APPLICABLE;
                break;
            case "unknown":
                genderEnum = Gender.UNKNOWN;
                break;
        }
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setMass(Integer mass) {
        this.mass = mass;
    }

    public void setMass(String mass){
       if(mass != null && mass.matches("[0-9.]+")){
           this.mass = Integer.parseInt(mass);
       }
       else this.mass = -1;
    }

    public void setFilms(List<String> films) {
        this.films = films;
    }

    public void setUrl(String url){
        this.url = url;
    }

    @Override
    public String toString() {
        return "PersonSWAPI{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth_year='" + birth_year + '\'' +
                ", gender=" + gender +
                ", height=" + height +
                ", mass=" + mass +
                ", films=" + films +
                '}';
    }
}