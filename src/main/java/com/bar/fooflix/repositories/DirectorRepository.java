package com.bar.fooflix.repositories;

import com.bar.fooflix.entities.Director;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface DirectorRepository extends GraphRepository<Director> {
    Director findById(String id);
}
