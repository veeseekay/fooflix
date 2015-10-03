package com.bar.fooflix.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.graphdb.Direction;
import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.template.Neo4jOperations;

import javax.annotation.Generated;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
})
@NodeEntity
public class User {
    public static final String FRIEND = "FRIEND";
    public static final String RATED = "RATED";
    public static final String REVIEWED = "REVIEWED";
    public static final String UPVOTED = "UPVOTED";
    public static final String DOWNVOTED = "DOWNVOTED";
    public static final String COMMENTS_ON = "COMMENTS_ON";

    @GraphId
    Long nodeId;
    @Indexed
    String login;
    String name;
    String info;

    @JsonBackReference(value = "ratings")
    @RelatedToVia(type = RATED)
    @Fetch
    Set<Rating> ratings;

    @JsonBackReference(value = "favorites")
    @RelatedTo(type = RATED)
    Set<Movie> favorites;

    @JsonBackReference(value = "downvotes")
    @RelatedToVia(type = DOWNVOTED)
    Set<Downvote> downvotes;

    @JsonBackReference(value = "downvoted-reviews")
    @RelatedTo(type = DOWNVOTED)
    Set<Review> downvotedReviews;

    @JsonBackReference(value = "upvotes")
    @RelatedToVia(type = UPVOTED)
    Set<Upvote> upvotes;

    @JsonBackReference(value = "upvoted-reviews")
    @RelatedTo(type = UPVOTED)
    Set<Review> upvotedReviews;

    @JsonBackReference(value = "friends")
    @RelatedTo(type = FRIEND, direction = Direction.BOTH)
    @Fetch
    Set<User> friends;

    @JsonBackReference(value = "reviews")
    @RelatedTo(type = REVIEWED, direction = Direction.OUTGOING)
    @Fetch
    Set<Review> reviews;

    @JsonBackReference(value = "comments")
    @RelatedToVia
    Collection<Comment> comments;

    public User() {
    }


    public User(String login, String name, String password) {
        this.login = login;
        this.name = name;
    }

    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }


    public Rating rate(Neo4jOperations template, Movie movie, int stars, String comment) {
        final Rating rating = template.createRelationshipBetween(this, movie, Rating.class, RATED, false).rate(stars, comment);
        return template.save(rating);
    }

    public Upvote upvote(Neo4jOperations template, Review review) {
        final Upvote upvote = template.createRelationshipBetween(this, review, Upvote.class, UPVOTED, false).upvote();
        return template.save(upvote);
    }

    public Downvote downvote(Neo4jOperations template, Review review) {
        final Downvote downvote = template.createRelationshipBetween(this, review, Downvote.class, DOWNVOTED, false).downvote();
        return template.save(downvote);
    }

    public Comment comment(Neo4jOperations template, Review review, String userSays) {
        final Comment comment = template.createRelationshipBetween(this, review, Comment.class, COMMENTS_ON, false).comment(userSays);
        return template.save(comment);
    }

    public Collection<Rating> getRatings() {
        if(ratings != null)
            return IteratorUtil.asCollection(ratings);
        else
            return IteratorUtil.asCollection(new HashSet<Rating>());
    }

    @Override
    public String toString() {
        return String.format("%s (%s - %s)", name, login, nodeId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public String getLogin() {
        return login;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isFriend(User other) {
        return other != null && getFriends().contains(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        if (nodeId == null) return super.equals(o);
        return nodeId.equals(user.nodeId);

    }

    public Long getId() {
        return nodeId;
    }

    @Override
    public int hashCode() {

        return nodeId != null ? nodeId.hashCode() : super.hashCode();
    }
}