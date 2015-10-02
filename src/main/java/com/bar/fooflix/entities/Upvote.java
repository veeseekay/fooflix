package com.bar.fooflix.entities;

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
@RelationshipEntity(type = "UPVOTES")
public class Upvote {

    @GraphId
    Long id;

    //@JsonBackReference(value = "upvotes")
    @Fetch
    @StartNode
    User user;

    //@JsonBackReference
    @Fetch
    @EndNode
    Review review;

    int upvote = 0;

    public Upvote() {}

    public Upvote upvote() {
        this.upvote = 1;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Review getReview() {
        return review;
    }

    public int getUpvote() {
        return upvote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Upvote rating = (Upvote) o;
        if (id == null) return super.equals(o);
        return id.equals(rating.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }
}
