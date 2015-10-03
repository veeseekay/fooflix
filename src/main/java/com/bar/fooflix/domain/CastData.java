package com.bar.fooflix.domain;

import com.bar.fooflix.entities.Actor;
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
        "actors"
})
@QueryResult
public class CastData {

    @JsonIgnore
    @ResultColumn("movie")
    Movie movie;

    @JsonProperty("actors")
    @ResultColumn("actors")
    Collection<Actor> cast;

    @JsonIgnore
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @JsonProperty("actors")
    public Collection<Actor> getCast() {
        return cast;
    }

    public void setCast(Collection<Actor> cast) {
        this.cast = cast;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "movie=" + movie.getTitle() +
                ", cast=" + cast +
                '}';
    }
}
