package com.bar.fooflix.controllers;

import com.bar.fooflix.entities.hello.Person;
import com.bar.fooflix.repositories.hello.PersonRepository;
import org.neo4j.graphdb.Transaction;
import org.neo4j.io.fs.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;

@EnableWebMvc
@RestController
@RequestMapping("fooflix/v1/hello")
@EnableAutoConfiguration
@ComponentScan
public class HelloController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    GraphDatabase graphDatabase;

    private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);

    // Ensure neo4j is embedded and browser is started at localhost:7575
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> hello(@RequestHeader HttpHeaders headers,
            @RequestParam(value = "row", required = false) Integer row,
            @RequestParam(value = "size", required = false) Integer size) throws Exception {

        FileUtils.deleteRecursively(new File("accessingdataneo4j.db"));

        Person greg = new Person("Greg");
        Person roy = new Person("Roy");
        Person craig = new Person("Craig");
        Person ivan = new Person("Ivan");

        LOG.info("Before linking up with Neo4j...");
        for (Person person : new Person[] { greg, roy, craig, ivan }) {
            LOG.info(person.toString());
        }

        Transaction tx = graphDatabase.beginTx();
        try {
            personRepository.save(greg);
            personRepository.save(roy);
            personRepository.save(craig);
            personRepository.save(ivan);

            greg = personRepository.findByName(greg.name);
            greg.worksWith(roy);
            greg.worksWith(craig);
            greg.friendsWith(ivan);
            personRepository.save(greg);

            roy = personRepository.findByName(roy.name);
            roy.worksWith(craig);
            // We already know that roy works with greg
            personRepository.save(roy);

            // We already know craig works with roy and greg

            LOG.info("Lookup each person by name...");
            for (String name : new String[] { greg.name, roy.name, craig.name }) {
                LOG.info(personRepository.findByName(name).toString());
            }

            LOG.info("Looking up who works with Greg...");
            for (Person person : personRepository.findByTeammatesName("Greg")) {
                LOG.info(person.name + " works with Greg.");
            }

            tx.success();
        } finally {
            tx.close();
        }

        return new ResponseEntity<>(new String(), HttpStatus.OK);
    }
}