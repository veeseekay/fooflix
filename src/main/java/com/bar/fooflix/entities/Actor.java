package com.bar.fooflix.entities;

import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Collection;

@NodeEntity
public class Actor extends Person {
    public Actor(String id, String name) {
        super(id, name);
    }

    public Actor() {
    }

    @RelatedToVia
    Collection<Role> roles;

    public Actor(String id) {
        super(id,null);
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