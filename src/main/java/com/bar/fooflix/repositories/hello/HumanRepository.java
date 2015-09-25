package com.bar.fooflix.repositories.hello;

import com.bar.fooflix.entities.hello.Human;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface HumanRepository extends CrudRepository<Human, String> {

    Human findByName(String name);

    Collection<Human> findByTeammatesName(String name);

}
