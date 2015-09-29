package com.bar.fooflix.entities.hello;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Human {

    public String name;
    @RelatedTo(type = "TEAMMATE", direction = Direction.BOTH)
    public
    @Fetch
    Set<Human> teammates;
    @RelatedTo(type = "FRIEND", direction = Direction.BOTH)
    public
    @Fetch
    Set<Human> friends;
    @GraphId
    Long id;

    public Human() {
    }

    public Human(String name) {
        this.name = name;
    }

    public void worksWith(Human human) {
        if (teammates == null) {
            teammates = new HashSet<>();
        }
        teammates.add(human);
    }

    public void friendsWith(Human human) {
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(human);
    }

    public String toString() {
        String results = name + "'s teammates include\n";
        if (teammates != null) {
            for (Human human : teammates) {
                results += "\t- " + human.name + "\n";
            }
        }
        results += name + "'s friends include\n";
        if (friends != null) {
            for (Human human : friends) {
                results += "\t- " + human.name + "\n";
            }
        }
        return results;
    }
}
