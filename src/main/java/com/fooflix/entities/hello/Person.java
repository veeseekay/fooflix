package com.fooflix.entities.hello;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Person {

    @GraphId Long id;
    public String name;

    public Person() {}
    public Person(String name) { this.name = name; }

    @RelatedTo(type="TEAMMATE", direction=Direction.BOTH)
    public @Fetch Set<Person> teammates;

    @RelatedTo(type="FRIEND", direction=Direction.BOTH)
    public @Fetch Set<Person> friends;

    public void worksWith(Person person) {
        if (teammates == null) {
            teammates = new HashSet<Person>();
        }
        teammates.add(person);
    }

    public void friendsWith(Person person) {
        if (friends == null) {
            friends = new HashSet<Person>();
        }
        friends.add(person);
    }

    public String toString() {
        String results = name + "'s teammates include\n";
        if (teammates != null) {
            for (Person person : teammates) {
                results += "\t- " + person.name + "\n";
            }
        }
        results += name + "'s friends include\n";
        if (friends != null) {
            for (Person person : friends) {
                results += "\t- " + person.name + "\n";
            }
        }
        return results;
    }

}
