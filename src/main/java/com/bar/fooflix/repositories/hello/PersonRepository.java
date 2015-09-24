package com.bar.fooflix.repositories.hello;

import com.bar.fooflix.entities.hello.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PersonRepository extends CrudRepository<Person, String> {

    Person findByName(String name);

    Collection<Person> findByTeammatesName(String name);

}
