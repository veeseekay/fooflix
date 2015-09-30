package com.bar.fooflix.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
})
@RelationshipEntity(type = "DOWNVOTES")
public class Downvote {

    @GraphId
    Long id;

    @JsonBackReference
    @Fetch
    @StartNode
    User user;

    @JsonBackReference
    @EndNode
    @Fetch
    Review review;

    int downvote = 0;

    public Downvote() {}

    public Downvote downvote() {
        this.downvote = 1;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Review getReview() {
        return review;
    }

    public int getDownvote() {
        return downvote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Downvote rating = (Downvote) o;
        if (id == null) return super.equals(o);
        return id.equals(rating.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }
}
