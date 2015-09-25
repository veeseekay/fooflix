package com.bar.fooflix.repositories;

import com.bar.fooflix.entities.Actor;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

public interface ActorRepository extends GraphRepository<Actor>, RelationshipOperationsRepository<Actor> {
    Actor findById(String id);
}