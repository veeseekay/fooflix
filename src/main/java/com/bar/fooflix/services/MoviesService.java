package com.bar.fooflix.services;


import com.bar.fooflix.domain.CastData;
import com.bar.fooflix.domain.CrewData;
import com.bar.fooflix.domain.MovieData;
import com.bar.fooflix.entities.Actor;
import com.bar.fooflix.entities.Director;
import com.bar.fooflix.entities.Genre;
import com.bar.fooflix.entities.Movie;
import com.bar.fooflix.entities.Rating;
import com.bar.fooflix.entities.Review;
import com.bar.fooflix.entities.User;
import com.bar.fooflix.repositories.ActorRepository;
import com.bar.fooflix.repositories.DirectorRepository;
import com.bar.fooflix.repositories.MovieRepository;
import com.bar.fooflix.repositories.UserRepository;
import com.bar.fooflix.utils.TmdbJsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
public class MoviesService {

    private static final Logger LOG = LoggerFactory.getLogger(MoviesService.class);

    @Value("${max.actors:5}")
    int maxActors;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Neo4jOperations template;

    @Transactional
    public Page<Movie> getMovies(Pageable pageable) throws Exception {
        return movieRepository.findAll(pageable);
    }

    @Transactional
    public List<Movie> addMovies(List<Map> movieData) throws Exception {
        List<Movie> savedMovies = new ArrayList<>();
        for (Map movieInfo : movieData) {
            Set<Genre> genres = new HashSet<>();
            Movie movie = new Movie("" + (new Random().nextInt(Integer.MAX_VALUE)), null);
            TmdbJsonMapper.mapToMovie(movieInfo, genres, movie);
            savedMovies.add(movieRepository.save(movie));
        }
        return savedMovies;
    }

    @Transactional
    public Iterable<Movie> updateMovies(List<Movie> movies) {
        List<Movie> savedMovies = new ArrayList<>();
        for (Movie movie : movies) {
            Movie repoMovie = movieRepository.findById(movie.getId());

            LOG.info("repoMovie by id {} = {}", repoMovie.getId(), repoMovie);
            if (repoMovie != null) {
                mergeMovie(movie, repoMovie);
                savedMovies.add(movieRepository.save(repoMovie));
            } else {
                LOG.error("Movie {} not found", movie.getId());
            }
        }
        return savedMovies;
    }

    @Transactional
    public Movie getMovie(String id) throws Exception {
        MovieData md = movieRepository.getAMovie(id);
        LOG.debug("Movie data after getting a movie is {}", md);

        Movie movie = movieRepository.findById(id);
        LOG.debug("Movie by id {} is {} with cast {} and directors {}", id, movie, movie.getActors(), movie.getDirectors());
        LOG.debug("Movie by id {} has roles {}", id, movie.getRoles());

        return movie;
    }


    @Transactional
    public CrewData getCrew(String id) throws Exception {
        CrewData md = movieRepository.getCrew(id);
        LOG.debug("Movie data after getting a movie is {}", md);

        return md;
    }

    @Transactional
    public CastData getCast(String id) throws Exception {
        CastData md = movieRepository.getCast(id);
        LOG.debug("Movie data after getting a movie is {}", md);

        return md;
    }


    @Transactional
    public List<Actor> addCast(String id, List<Actor> cast) throws Exception {
        List<Actor> savedActors = new ArrayList<>();
        Movie movie = movieRepository.findById(id);
        LOG.info("Movie {} = {} ", id, movie);

        for (Actor actor : cast) {
            Actor repoActor = actorRepository.findByName(actor.getName());
            String role = cast.get(0).getRoles().iterator().next().getName();
            if(repoActor == null) {
                actor.setId("" + new Random().nextInt(Integer.MAX_VALUE));
                actor.setRoles(new HashSet<>());
                template.save(actor);
                actor.playedIn(movie, role);
                savedActors.add(actorRepository.save(actor));
            } else {
                repoActor.playedIn(movie, role);
                savedActors.add(actorRepository.save(repoActor));
            }
        }
        return savedActors;
    }

    @Transactional
    public List<Director> addCrew(String id, List<Director> crew) throws Exception {
        List<Director> savedDirectors = new ArrayList<>();
        for (Director director : crew) {
            director.setId("" + new Random().nextInt(Integer.MAX_VALUE));
            template.save(director);
            Movie movie = movieRepository.findById(id);
            LOG.info("Movie {} = {} ", id, movie);
            director.directed(movie);
            savedDirectors.add(directorRepository.save(director));
        }
        return savedDirectors;
    }

    @Transactional
    public Page<Rating> getRatings(String movieId, Pageable pageable) throws Exception {
        return movieRepository.findRatings(movieId, pageable);
    }

    @Transactional
    public Page<Review> getReviews(String movieId, Pageable pageable) throws Exception {
        return movieRepository.findReviews(movieId, pageable);
    }

    @Transactional
    public List<Review> saveReviews(String userLogin, String id, List<Review> reviews) {
        Movie movie = movieRepository.findById(id);
        User user = userRepository.findByLogin(userLogin);
        Review review = template.save(reviews.get(0));
        user.addReview(review);
        review.reviewed(movie);
        template.save(review);
        template.save(user);
;        return new ArrayList<Review>() {{
            add(review);
        }};
    }

    @Transactional
    public Collection<Rating> saveRatings(String userLogin, String id, List<Rating> rating) {

        LOG.info("user {} has rated movie {} as {} commented {}", userLogin, id, rating.get(0).getStars(), rating.get(0).getComment());

        Movie movie = movieRepository.findById(id);
        User u = userRepository.findByLogin(userLogin);
        u.rate(template, movie, rating.get(0).getStars(), rating.get(0).getComment());

        return rating;
    }

    private void mergeMovie(Movie movie, Movie repoMovie) {
        repoMovie.setTitle(movie.getTitle());
        repoMovie.setGenre(movie.getGenre());
    }
}
