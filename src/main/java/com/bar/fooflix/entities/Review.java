package com.bar.fooflix.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import javax.annotation.Generated;
import java.util.Set;

import static org.neo4j.graphdb.Direction.INCOMING;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
})
@NodeEntity
public class Review {
    @GraphId
    Long id;

    String oneWord;

    String review;

    @JsonBackReference
    @RelatedTo(elementClass = Movie.class, type = "HAS_REVIEW")
    private Movie movie;

    @JsonBackReference
    @RelatedTo(elementClass = User.class, type = "REVIEWED")
    private User user;

    @RelatedToVia(type = "UPVOTED", direction = INCOMING)
    @Fetch
    Set<Upvote> upvotes;

    @RelatedToVia(type = "DOWNVOTED", direction = INCOMING)
    @Fetch
    Set<Downvote> downvotes;

    @RelatedToVia(type = "COMMENTS_ON", direction = INCOMING)
    @Fetch
    Set<Comment> comments;

    public Review() {
    }

    public Review(String oneWord, String review) {
        this.oneWord = oneWord;
        this.review = review;
    }

    public int getDownvotes() {
        if(downvotes == null) {
            return 0;
        }
        return downvotes.size();
    }


    public int getUpvotes() {
        if(upvotes == null) {
            return 0;
        }
        return upvotes.size();
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Movie getMovie() {
        return movie;
    }

    public void reviewed(Movie movie) {
        this.movie = movie;
    }
}
