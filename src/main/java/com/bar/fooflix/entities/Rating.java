package com.bar.fooflix.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.neo4j.annotation.*;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
})
@RelationshipEntity
public class Rating {
    private static final int MAX_STARS = 5;
    private static final int MIN_STARS = 0;
    @GraphId Long id;

    @StartNode User user;
    @EndNode Movie movie;

    int stars;
    String comment;

    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Rating rate(int stars, String comment) {
        if (stars>= MIN_STARS && stars <= MAX_STARS) this.stars=stars;
        if (comment!=null && !comment.isEmpty()) this.comment = comment;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;
        if (id == null) return super.equals(o);
        return id.equals(rating.id);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

}
