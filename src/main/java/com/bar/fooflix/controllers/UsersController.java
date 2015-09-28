package com.bar.fooflix.controllers;

import com.bar.fooflix.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.Valid;


@EnableWebMvc
@RestController
@RequestMapping("fooflix/v1/users")
@EnableAutoConfiguration
@ComponentScan
public class UsersController {

    @Autowired
    UsersService usersService;

    private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers(@RequestHeader HttpHeaders headers,
            @RequestParam(value = "row", required = false) Integer row,
            @RequestParam(value = "size", required = false) Integer size) throws Exception {

        return new ResponseEntity<>("{well}", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUsers(@RequestHeader HttpHeaders headers, @RequestBody Object cmd) throws Exception {

        return new ResponseEntity<>("{well}", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> bulkUpdateUsers(@RequestHeader HttpHeaders headers, @Valid @RequestBody Object cmd)
            throws Exception {

        return new ResponseEntity<>("{well}", HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@RequestHeader HttpHeaders headers, @PathVariable String id) throws Exception {
        return new ResponseEntity<>("{well}", HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@RequestHeader HttpHeaders headers, @PathVariable String id,
            @RequestBody Object cmd) throws Exception {

        return new ResponseEntity<>("{well}", HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestHeader HttpHeaders headers, @PathVariable String id,
            @RequestBody Object cmd) throws Exception {

        return new ResponseEntity<>("{well}", HttpStatus.OK);
    }
}