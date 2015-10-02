package com.bar.fooflix.tests;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import javax.annotation.Generated;

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
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

    @JsonBackReference(value = "has_review")
    @RelatedTo(elementClass = Movie.class, type = "HAS_REVIEW")
    private Movie movie;

    @JsonBackReference(value = "reviews")
    @RelatedTo(elementClass = User.class, type = "REVIEWED")
    private User user;

    /*@RelatedToVia(type = "UPVOTED", direction = INCOMING)
    @Fetch
    Set<Upvote> upvotes;

    @RelatedToVia(type = "DOWNVOTED", direction = INCOMING)
    @Fetch
    Set<Downvote> downvotes;

    @RelatedToVia(type = "COMMENTS_ON", direction = INCOMING)
    @Fetch
    Set<Comment> comments;*/

    public Review() {
    }

    public Review(String oneWord, String review) {
        this.oneWord = oneWord;
        this.review = review;
    }

    /*public int getDownvotes() {
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
    }*/

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
