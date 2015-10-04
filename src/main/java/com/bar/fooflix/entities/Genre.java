package com.bar.fooflix.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import javax.annotation.Generated;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
@JsonPropertyOrder({
})
@NodeEntity
public class Genre {

    @GraphId
    Long id;

    @JsonProperty("name")
    String name;

    @JsonIgnore
    @RelatedTo(elementClass = Movie.class, type = "HAS_MOVIE")
    private Set<Movie> moviesOfGenre = new HashSet<>();

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Set<Movie> getMoviesOfGenre() {
        return moviesOfGenre;
    }

    public void hasMovie(Movie movie) {
        this.moviesOfGenre.add(movie);
    }
}
