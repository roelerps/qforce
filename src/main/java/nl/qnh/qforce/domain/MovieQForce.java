package nl.qnh.qforce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MovieQForce implements Movie{


    private String title;
    private Integer episode;
    private String director;
    private LocalDate releaseDate;

    /**
     * Returns the title.
     *
     * @return the title
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Returns the episode number.
     *
     * @return the episode number
     */
    @Override
    public Integer getEpisode() {
        return episode;
    }

    /**
     * Returns the name of the person who directed the movie.
     *
     * @return the name of the director
     */
    @Override
    public String getDirector() {
        return director;
    }

    /**
     * The release date of the movie.
     *
     * @return the release date
     */
    @Override
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "MovieQForce{" +
                "title='" + title + '\'' +
                ", episode=" + episode +
                ", director='" + director + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
