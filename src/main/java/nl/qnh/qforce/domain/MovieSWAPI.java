package nl.qnh.qforce.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieSWAPI implements Movie{


    private String title;
    private Integer episode_id;
    private String director;
    //@JsonFormat(pattern = "yyyy/mm/dd")
    private LocalDate release_date;

    @JsonCreator
    public MovieSWAPI() {
        ;
    }

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
        return episode_id;
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
        return release_date;
    }

    public void setEpisode_id(Integer episodeId) {
        this.episode_id = episodeId;
    }

    public void setRelease_date(LocalDate releaseDate) {
        this.release_date = releaseDate;
    }

    @Override
    public String toString() {
        return "MovieSWAPI{" +
                "title='" + title + '\'' +
                ", episodeId=" + episode_id +
                ", director='" + director + '\'' +
                ", releaseDate=" + release_date +
                '}';
    }
}
