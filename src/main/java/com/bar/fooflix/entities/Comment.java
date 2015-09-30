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
@RelationshipEntity(type = "COMMENTS_ON")
public class Comment {
    @GraphId
    Long id;

    String comment;

    @JsonBackReference
    @Fetch
    @StartNode
    private User user;

    @JsonBackReference
    @Fetch
    @EndNode
    private Review review;

    public Comment() {
    }

    public Comment(Review review, String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Comment comment(String comment) {
        this.comment = comment;
        return this;
    }
}
