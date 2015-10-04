package com.bar.fooflix.controllers;

import com.bar.fooflix.repositories.UserRepository;
import com.bar.fooflix.services.DbLoadService;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@EnableWebMvc
@RestController
@RequestMapping("fooflix/v1/load")
@EnableAutoConfiguration
@ComponentScan
public class DbLoadController {

    private static final Logger LOG = LoggerFactory.getLogger(DbLoadController.class);

    @Autowired
    DbLoadService importService;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/{ids}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> load(@RequestHeader HttpHeaders headers,
            @PathVariable String ids) throws Exception {

        // Alright, lets get some data loaded.
        long start = System.currentTimeMillis();

        // lets ignore path variable ids for now
        // ids = "603,605,604,87101,157336,534,62,218,78,644,2048,152,411,95";

        final Map<Integer, String> movies = importService.importMovies(extractRanges(ids));

        long duration = (System.currentTimeMillis() - start) / 1000;

        return new ResponseEntity<>(duration, HttpStatus.OK);
    }

    private List<Integer> extractRanges(String ids) {
        List<Integer> ranges = new ArrayList<>();
        for (String token : ids.split(",")) {
            int id = Integer.parseInt(token);
            ranges.add(id);
        }
        return ranges;
    }
}