package com.bar.fooflix.services;


import com.bar.fooflix.entities.Movie;
import com.bar.fooflix.repositories.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecommendationsService {

    private static final Logger LOG = LoggerFactory.getLogger(RecommendationsService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public Page<Movie> getRecommendationsByGenre(String genre, Pageable pageable) throws Exception {
        return movieRepository.getRecommendationsByGenre(genre, pageable);
    }

    @Transactional
    public Page<Movie> getRecommendationsByActor(String actor, Pageable pageable) throws Exception {
        return movieRepository.getRecommendationsByActor(actor, pageable);
    }

    @Transactional
    public Page<Movie> getRecommendationsByUserRating(String user, Pageable pageable) throws Exception {
        return movieRepository.getRecommendationsByUserRating(user, pageable);
    }
}
