package com.bar.fooflix.domain;

import com.bar.fooflix.entities.Director;
import com.bar.fooflix.entities.Movie;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
})
@QueryResult
public class CrewData {

    @JsonIgnore
    @ResultColumn("movie")
    Movie movie;

    @JsonProperty("directors")
    @ResultColumn("directors")
    Collection<Director> directors;

    @JsonIgnore
    public Collection<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Collection<Director> directors) {
        this.directors = directors;
    }

    @JsonIgnore
    @JsonProperty("movie")
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "movie=" + movie.getTitle() +
                ", directors=" + directors +
                '}';
    }
}
