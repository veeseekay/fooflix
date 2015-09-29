package com.bar.fooflix.entities;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
})
@NodeEntity
public class User {
    public static final String FRIEND = "FRIEND";
    public static final String RATED = "RATED";
    @GraphId
    Long nodeId;
    @Indexed
    String login;
    String name;
    String info;
    @RelatedToVia(type = RATED)
    Set<Rating> ratings;
    @RelatedTo(type = RATED)
    Set<Movie> favorites;
    @RelatedTo(type = FRIEND, direction = Direction.BOTH)
    @Fetch
    Set<User> friends;

    public User() {
    }


    public User(String login, String name, String password) {
        this.login = login;
        this.name = name;
    }

    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    public Rating rate(Neo4jOperations template, Movie movie, int stars, String comment) {
        final Rating rating = template.createRelationshipBetween(this, movie, Rating.class, RATED, false).rate(stars, comment);
        return template.save(rating);
    }

    public Collection<Rating> getRatings() {
        return IteratorUtil.asCollection(ratings);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, login);
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