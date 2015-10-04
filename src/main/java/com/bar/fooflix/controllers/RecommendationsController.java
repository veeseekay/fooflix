package com.bar.fooflix.controllers;

import com.bar.fooflix.entities.Movie;
import com.bar.fooflix.services.RecommendationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@RestController
@RequestMapping("fooflix/v1/recommendations")
@EnableAutoConfiguration
@ComponentScan
public class RecommendationsController {

    private static final Logger LOG = LoggerFactory.getLogger(RecommendationsController.class);

    @Autowired
    RecommendationsService recommendationsService;

    @RequestMapping(value = "/genre", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<PagedResources<Movie>> getRecommendationsByGenre(@RequestHeader HttpHeaders headers,
            @RequestParam String name, Pageable pageable, PagedResourcesAssembler assembler) throws Exception {

        Page<Movie> movies = recommendationsService.getRecommendationsByGenre(name, pageable);
        return new ResponseEntity<>(assembler.toResource(movies), HttpStatus.OK);
    }

    @RequestMapping(value = "/actor", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<PagedResources<Movie>> getRecommendationsByActor(@RequestHeader HttpHeaders headers,
            @RequestParam String name, Pageable pageable, PagedResourcesAssembler assembler) throws Exception {

        Page<Movie> movies = recommendationsService.getRecommendationsByActor(name, pageable);
        return new ResponseEntity<>(assembler.toResource(movies), HttpStatus.OK);
    }
}