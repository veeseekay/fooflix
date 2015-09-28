package com.bar.fooflix.domain;

import com.bar.fooflix.entities.Actor;
import com.bar.fooflix.entities.Director;
import com.bar.fooflix.entities.Movie;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

import java.util.Collection;

@QueryResult
public class MovieData {

    @ResultColumn("movie")
    Movie movie;

    @ResultColumn("directors")
    Collection<Director> directors;

    @ResultColumn("actors")
    Collection<Actor> cast;

    public Collection<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Collection<Director> directors) {
        this.directors = directors;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

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
                ", directors=" + directors +
                ", cast=" + cast +
                '}';
    }
}
