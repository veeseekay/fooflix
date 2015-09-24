package com.fooflix.controllers;

import javax.validation.Valid;

import com.fooflix.repositories.hello.PersonRepository;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@RestController
@RequestMapping("fooflix/v1/movies")
@EnableAutoConfiguration
@ComponentScan
public class MoviesController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    GraphDatabase graphDatabase;

    private static final Logger LOG = LoggerFactory.getLogger(MoviesController.class);


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMovies(@RequestHeader HttpHeaders headers,
            @RequestParam(value = "row", required = false) Integer row,
            @RequestParam(value = "size", required = false) Integer size) throws Exception {

        return new ResponseEntity<>(new String(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> bulkUpdateMovies(@RequestHeader HttpHeaders headers, @Valid @RequestBody Object cmd)
            throws Exception {


        return new ResponseEntity<>(new String(), HttpStatus.OK);
    }
}