package com.bar.fooflix.services;


import com.bar.fooflix.entities.Movie;
import com.bar.fooflix.repositories.ActorRepository;
import com.bar.fooflix.repositories.DirectorRepository;
import com.bar.fooflix.repositories.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MoviesService {

    private static final Logger LOG = LoggerFactory.getLogger(MoviesService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Transactional
    public Movie getMovie(String id) throws Exception {
        Movie movie = movieRepository.findById(id);
        LOG.debug("movie is {}", new ObjectMapper().writeValueAsString(movie));
        LOG.debug("dir = " + movie.getDirectors());

        LOG.debug("dir 9340 " + directorRepository.findById("9340").toString());

        LOG.debug("actor 6384 " + actorRepository.findById("6384").toString());

        return movie;
    }
}
