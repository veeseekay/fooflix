package com.bar.fooflix.controllers;

import com.bar.fooflix.entities.hello.Human;
import com.bar.fooflix.repositories.hello.HumanRepository;
import org.neo4j.graphdb.Transaction;
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

@EnableWebMvc
@RestController
@RequestMapping("fooflix/v1/hello")
@EnableAutoConfiguration
@ComponentScan
public class HelloController {

    @Autowired
    HumanRepository humanRepository;

    @Autowired
    GraphDatabase graphDatabase;

    private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);

    // Ensure neo4j is embedded and browser is started at localhost:7575
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> hello(@RequestHeader HttpHeaders headers,
            @RequestParam(value = "row", required = false) Integer row,
            @RequestParam(value = "size", required = false) Integer size) throws Exception {

        Human greg = new Human("Greg");
        Human roy = new Human("Roy");
        Human craig = new Human("Craig");
        Human ivan = new Human("Ivan");

        LOG.info("Before linking up with Neo4j...");
        for (Human human : new Human[] { greg, roy, craig, ivan }) {
            LOG.info(human.toString());
        }

        Transaction tx = graphDatabase.beginTx();
        try {
            humanRepository.save(greg);
            humanRepository.save(roy);
            humanRepository.save(craig);
            humanRepository.save(ivan);

            greg = humanRepository.findByName(greg.name);
            greg.worksWith(roy);
            greg.worksWith(craig);
            greg.friendsWith(ivan);
            humanRepository.save(greg);

            roy = humanRepository.findByName(roy.name);
            roy.worksWith(craig);
            // We already know that roy works with greg
            humanRepository.save(roy);

            // We already know craig works with roy and greg

            LOG.info("Lookup each human by name...");
            for (String name : new String[] { greg.name, roy.name, craig.name }) {
                LOG.info(humanRepository.findByName(name).toString());
            }

            LOG.info("Looking up who works with Greg...");
            for (Human human : humanRepository.findByTeammatesName("Greg")) {
                LOG.info(human.name + " works with Greg.");
            }

            tx.success();
        } finally {
            tx.close();
        }

        return new ResponseEntity<>(new String(), HttpStatus.OK);
    }
}