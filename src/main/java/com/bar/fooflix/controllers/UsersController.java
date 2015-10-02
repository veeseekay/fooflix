package com.bar.fooflix.controllers;

import com.bar.fooflix.entities.User;
import com.bar.fooflix.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;


@EnableWebMvc
@RestController
@RequestMapping("fooflix/v1/users")
@EnableAutoConfiguration
@ComponentScan
public class UsersController {

    private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    UsersService usersService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers(@RequestHeader HttpHeaders headers,
            Pageable pageable, PagedResourcesAssembler assembler) throws Exception {

        Page<User> users = usersService.getUsers(pageable);
        return new ResponseEntity<>(assembler.toResource(users), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUsers(@RequestHeader HttpHeaders headers, @RequestBody List<User> users) throws Exception {

        LOG.debug("Users to add {}", users);
        return new ResponseEntity<>(usersService.addUsers(users), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> bulkUpdateUsers(@RequestHeader HttpHeaders headers, @RequestBody List<User> users)
            throws Exception {

        LOG.debug("Users to update {}", users);
        return new ResponseEntity<>(usersService.updateUsers(users), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@RequestHeader HttpHeaders headers, @PathVariable String id) throws Exception {
        return new ResponseEntity<>(usersService.getUser(id), HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestHeader HttpHeaders headers, @PathVariable String id,
            @RequestBody User user) throws Exception {

        return new ResponseEntity<>(usersService.updateUser(user), HttpStatus.OK);
    }
}