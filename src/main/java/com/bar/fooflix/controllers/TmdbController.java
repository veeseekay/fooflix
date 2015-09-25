package com.bar.fooflix.controllers;

import com.bar.fooflix.services.TmdbImportService;
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

import java.util.LinkedHashMap;
import java.util.Map;

@EnableWebMvc
@RestController
@RequestMapping("fooflix/v1/load")
@EnableAutoConfiguration
@ComponentScan
public class TmdbController {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbController.class);

    @Autowired
    TmdbImportService importService;

    @RequestMapping(value = "/{ids}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> load(@RequestHeader HttpHeaders headers,
            @PathVariable String ids) throws Exception {

        long start=System.currentTimeMillis();
        final Map<Integer, String> movies = importService.importMovies(extractRanges(ids));
        long duration = (System.currentTimeMillis() - start) / 1000;

        return new ResponseEntity<>(duration, HttpStatus.OK);
    }

    private Map<Integer, Integer> extractRanges(String ids) {
        Map<Integer, Integer> ranges = new LinkedHashMap<>();
        StringBuilder errors = new StringBuilder();
        for (String token : ids.split(",")) {
            try {
                if (token.contains("-")) {
                    String[] range = token.split("-");
                    ranges.put(Integer.parseInt(range[0]), Integer.parseInt(range[1]));
                } else {
                    int id = Integer.parseInt(token);
                    ranges.put(id, id);
                }
            } catch (Exception e) {
                errors.append(token).append(": ").append(e.getMessage()).append("\n");
            }
        }
        if (errors.length() > 0) {
            throw new RuntimeException("Error parsing ids\n" + errors);
        }
        return ranges;
    }
}