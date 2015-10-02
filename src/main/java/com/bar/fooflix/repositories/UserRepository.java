package com.bar.fooflix.repositories;

import com.bar.fooflix.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

public interface UserRepository extends GraphRepository<User>,
        RelationshipOperationsRepository<User> {

    User findByLogin(String login);

    Page<User> findAll(Pageable pageable);

}
