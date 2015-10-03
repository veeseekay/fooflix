package com.bar.fooflix.controllers;

import com.bar.fooflix.domain.CastData;
import com.bar.fooflix.domain.CrewData;
import com.bar.fooflix.entities.Actor;
import com.bar.fooflix.entities.Director;
import com.bar.fooflix.entities.Movie;
import com.bar.fooflix.entities.Rating;
import com.bar.fooflix.entities.Review;
import com.bar.fooflix.services.MoviesService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Map;


@EnableWebMvc
@RestController
@RequestMapping("fooflix/v1/movies")
@EnableAutoConfiguration
@ComponentScan
public class MoviesController {

    private static final Logger LOG = LoggerFactory.getLogger(MoviesController.class);
    @Autowired
    MoviesService moviesService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<PagedResources<Movie>> getMovies(@RequestHeader HttpHeaders headers,
            Pageable pageable, PagedResourcesAssembler assembler) throws Exception {


        Page<Movie> movies = moviesService.getMovies(pageable);
        return new ResponseEntity<>(assembler.toResource(movies), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMovies(@RequestHeader HttpHeaders headers, @RequestBody List<Map> moviesToAdd) throws Exception {

        LOG.info("Adding movies {}", moviesToAdd);
        return new ResponseEntity<>(moviesService.addMovies(moviesToAdd), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> bulkUpdateMovies(@RequestHeader HttpHeaders headers, @RequestBody List<Movie> moviesToUpdate)
            throws Exception {

        LOG.info("Updating movies {}", moviesToUpdate);
        return new ResponseEntity<>(moviesService.updateMovies(moviesToUpdate), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMovie(@RequestHeader HttpHeaders headers, @PathVariable String id) throws Exception {
        Movie m = moviesService.getMovie(id);
        //LOG.debug("Got movie {} with {} and directed by {}", m.getMovie().getTitle(), m.getCast(), m.getDirectors());
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMovie(@RequestHeader HttpHeaders headers, @PathVariable String id,
            @RequestBody Object cmd) throws Exception {

        return new ResponseEntity<>("{well}", HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMovie(@RequestHeader HttpHeaders headers, @PathVariable String id,
            @RequestBody Object cmd) throws Exception {

        return new ResponseEntity<>("{well}", HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/crew", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMovieCrew(@RequestHeader HttpHeaders headers,
            @PathVariable String id) throws Exception {
        CrewData md = moviesService.getCrew(id);
        return new ResponseEntity<>(md, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/crew", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMovieCrew(@RequestHeader HttpHeaders headers, @PathVariable String id,
            @RequestBody List<Director> crew) throws Exception {

        LOG.info("{} directors for {}", crew, id);
        return new ResponseEntity<>(moviesService.addCrew(id, crew), HttpStatus.OK);    }


    @RequestMapping(value = "/{id}/cast", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMovieCast(@RequestHeader HttpHeaders headers,
            @PathVariable String id) throws Exception {
        CastData md = moviesService.getCast(id);
        LOG.info("Cast from service  {}", md);
        return new ResponseEntity<>(md, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/cast", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMovieCast(@RequestHeader HttpHeaders headers, @PathVariable String id,
            @RequestBody List<Actor> cast) throws Exception {

        LOG.info("{} {} {} are casts for movie {}", cast.get(0).getBirthday(), cast.get(0).getBirthplace(), cast.get(0).getRoles(), id);
        return new ResponseEntity<>(moviesService.addCast(id, cast), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/ratings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMovieRatings(@RequestHeader HttpHeaders headers, @PathVariable String id,
            Pageable pageable, PagedResourcesAssembler assembler) throws Exception {

        Page<Rating> ratings = moviesService.getRatings(id, pageable);
        LOG.info("Movie ratings {}", ratings);
        return new ResponseEntity<>(assembler.toResource(ratings), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/ratings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMovieRatingsForUser(@RequestHeader HttpHeaders headers, @PathVariable String id,
            @RequestParam("userLogin") String userLogin, @RequestBody List<Rating> ratings) throws Exception {

        // Get user id from session ideally
        return new ResponseEntity<>(moviesService.saveRatings(userLogin, id, ratings), HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}/reviews", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMovieReviews(@RequestHeader HttpHeaders headers, @PathVariable String id,
            Pageable pageable, PagedResourcesAssembler assembler) throws Exception {

        Page<Review> reviews = moviesService.getReviews(id, pageable);
        return new ResponseEntity<>(assembler.toResource(reviews), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/reviews", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMovieReviewsForUser(@RequestHeader HttpHeaders headers, @PathVariable String id,
            @RequestParam("userLogin") String userLogin, @RequestBody List<Review> reviews) throws Exception {

        // Get user id from session ideally
        return new ResponseEntity<>(moviesService.saveReviews(userLogin, id, reviews), HttpStatus.OK);
    }


}