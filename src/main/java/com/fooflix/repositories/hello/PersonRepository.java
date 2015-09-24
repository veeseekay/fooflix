package com.fooflix.repositories.hello;

import com.fooflix.entities.hello.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String> {

    Person findByName(String name);

    Iterable<Person> findByTeammatesName(String name);

}
