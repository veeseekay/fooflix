package com.bar.fooflix.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import javax.annotation.Generated;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
})
@NodeEntity
public class Genre {
    @GraphId
    Long id;

    String name;

    @JsonBackReference
    @RelatedTo(elementClass = Movie.class, type = "HAS_MOVIE")
    private Set<Movie> moviesOfGenre = new HashSet<>();

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Movie> getMoviesOfGenre() {
        return moviesOfGenre;
    }

    public void hasMovie(Movie movie) {
        this.moviesOfGenre.add(movie);
    }
}
