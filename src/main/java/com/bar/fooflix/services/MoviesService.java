package com.bar.fooflix.services;


import com.bar.fooflix.domain.MovieData;
import com.bar.fooflix.entities.Movie;
import com.bar.fooflix.repositories.ActorRepository;
import com.bar.fooflix.repositories.DirectorRepository;
import com.bar.fooflix.repositories.MovieRepository;
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
    public MovieData getMovie(String id) throws Exception {
        MovieData md = movieRepository.getAMovie(id);
        LOG.debug("Movie data after getting a movie is {}", md);

        Movie movie = movieRepository.findById(id);
        LOG.debug("Movie by id {} is {} with cast {} and directors {}", id, movie, movie.getActors(), movie.getDirectors());

        return md;
    }
}
