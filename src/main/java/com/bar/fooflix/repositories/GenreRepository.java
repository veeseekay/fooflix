package com.bar.fooflix.repositories;

import com.bar.fooflix.entities.Genre;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

public interface GenreRepository extends GraphRepository<Genre>, RelationshipOperationsRepository<Genre> {
}
