package com.bar.fooflix.repositories;

import com.bar.fooflix.entities.Director;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

public interface DirectorRepository extends GraphRepository<Director>, RelationshipOperationsRepository<Director> {
    Director findById(String id);
}
