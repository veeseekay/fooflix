package com.bar.fooflix.tests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import javax.annotation.Generated;
import java.util.Collection;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
})
@NodeEntity
public class Actor extends Person {
    @JsonManagedReference(value = "acts_as")
    @RelatedToVia
    Collection<Role> roles;

    public Actor(String id, String name) {
        super(id, name);
    }

    public Actor() {
    }

    public Actor(String id) {
        super(id, null);
    }

    public Iterable<Role> getRoles() {
        return roles;
    }

    public Role playedIn(Movie movie, String roleName) {
        final Role role = new Role(this, movie, roleName);
        roles.add(role);
        return role;
    }
}