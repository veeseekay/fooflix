package com.bar.fooflix.services;


import com.bar.fooflix.domain.MovieData;
import com.bar.fooflix.entities.Actor;
import com.bar.fooflix.entities.Director;
import com.bar.fooflix.entities.Genre;
import com.bar.fooflix.entities.Movie;
import com.bar.fooflix.entities.Person;
import com.bar.fooflix.entities.Roles;
import com.bar.fooflix.repositories.ActorRepository;
import com.bar.fooflix.repositories.DirectorRepository;
import com.bar.fooflix.repositories.GenreRepository;
import com.bar.fooflix.repositories.MovieRepository;
import com.bar.fooflix.utils.TmdbJsonMapper;
import org.neo4j.helpers.collection.CombiningIterable;
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

import static org.neo4j.helpers.collection.IteratorUtil.asIterable;

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
    private GenreRepository genreRepository;

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

    private void relateGenresToMovie(Movie movie, Set<Genre> genres) {
        for (Genre genre : genres) {
            genre.hasMovie(movie);
            genreRepository.save(genre);
        }
    }

    private void relatePersonsToMovie(Movie movie, Map data) {

        int max = 0;
        @SuppressWarnings("unchecked") Iterable<Map> cast = (Collection<Map>) ((Map) data.get("credits")).get("cast");
        @SuppressWarnings("unchecked") Iterable<Map> crew = (Collection<Map>) ((Map) data.get("credits")).get("crew");
        for (Map entry : new CombiningIterable<>(asIterable(cast, crew))) {
            String id = "" + entry.get("id");
            String jobName = (String) entry.get("job");
            Roles job = TmdbJsonMapper.mapToRole(jobName);
            if (job == null) {
                // Dont worry about other job names for now, we are good with director.
                LOG.info("Could not add person with job " + jobName + " " + entry);
                continue;
            }
            switch (job) {
                case DIRECTED:
                    Director director = template.projectTo(doImportPerson(id, new Director(id)), Director.class);
                    directorRepository.save(director);
                    director.directed(movie);
                    LOG.info("{} {}", job, director);
                    directorRepository.save(director);
                    break;
                case ACTS_IN:
                    if (max < maxActors) {
                        final Actor actor = template.projectTo(doImportPerson(id, new Actor(id)), Actor.class);
                        actor.playedIn(movie, (String) entry.get("character"));
                        LOG.info("{} {}", job, actor);
                        actorRepository.save(actor);
                        max++;
                    }
                    break;
            }
        }
    }

    private <T extends Person> T doImportPerson(String personId, T newPerson) {

        LOG.debug("Importing person " + personId);
        Person person = template.findByIndexedValue(Person.class, "id", personId).to(Person.class).singleOrNull();
        if (person != null) return (T) person;
        Map data = loadPersonData(personId);
        if (data.containsKey("not_found")) throw new RuntimeException("Data for Person " + personId + " not found.");
        TmdbJsonMapper.mapToPerson(data, newPerson);
        LOG.debug("new person {}", newPerson);
        return template.save(newPerson);
    }

    private Map loadPersonData(String personId) {

        //Map data = client.getPerson(personId);
        return null;
    }

    private void mergeMovie(Movie movie, Movie repoMovie) {
        repoMovie.setTitle(movie.getTitle());
        repoMovie.setGenre(movie.getGenre());
    }
}
