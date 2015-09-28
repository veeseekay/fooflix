package com.bar.fooflix.services;


import com.bar.fooflix.entities.Actor;
import com.bar.fooflix.entities.Director;
import com.bar.fooflix.entities.Movie;
import com.bar.fooflix.entities.Person;
import com.bar.fooflix.entities.Roles;
import com.bar.fooflix.entities.User;
import com.bar.fooflix.repositories.ActorRepository;
import com.bar.fooflix.repositories.DirectorRepository;
import com.bar.fooflix.repositories.MovieRepository;
import com.bar.fooflix.repositories.UserRepository;
import com.bar.fooflix.utils.TmdbJsonMapper;
import org.neo4j.helpers.collection.CombiningIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.neo4j.helpers.collection.IteratorUtil.asIterable;

@Service
public class DbLoadService {

    private static final Logger LOG = LoggerFactory.getLogger(DbLoadService.class);

    @Value("${max.actors:5}")
    int maxActors;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TmdbApiClient client;

    @Autowired
    private Neo4jOperations template;

    @Transactional
    public Map<Integer, String> importMovies(List<Integer> ranges) {
        final Map<Integer, String> movies = new LinkedHashMap<Integer, String>();

        for(Integer id : ranges) {
        String result = importMovieFailsafe(id);
            movies.put(id, result);
        }

        // Now add some users
        addUsers();
        return movies;
    }

    private void addUsers() {
        // Now add some users
        User micha = userRepository.save(new User("micha", "Micha", "password"));
        User ollie = new User("ollie", "Olliver", "password");
        micha.addFriend(ollie);
        userRepository.save(micha);

        Movie movie = movieRepository.findById("603");
        micha.rate(template, movie, 5, "Best of the series");

        movie = movieRepository.findById("605");
        ollie.rate(template, movie, 2, "ok");
    }

    private String importMovieFailsafe(Integer id) {
        try {
            Movie movie = doImportMovie(String.valueOf(id));
            return movie.getTitle();
        } catch (Exception e) {
            LOG.error("Something failed when importing movie", e);
            return e.getMessage();
        }
    }

    private Movie doImportMovie(String movieId) {
        LOG.debug("Importing movie " + movieId);

        Movie movie = movieRepository.findById(movieId);
        LOG.debug("movie is {}", movie);
        if (movie == null) { // Not found: Create fresh
            movie = new Movie(movieId, null);
        }

        Map data = loadMovieData(movieId);
        if (data.containsKey("not_found")) throw new RuntimeException("Data for Movie " + movieId + " not found.");
        TmdbJsonMapper.mapToMovie(data, movie);
        movieRepository.save(movie);
        relatePersonsToMovie(movie, data);
        return movie;
    }

    private Map loadMovieData(String movieId) {
        Map data = client.getMovie(movieId);
        LOG.info("Movie with id {} has data {}", movieId, data);
        return data;
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
                    if(max < maxActors) {
                        final Actor actor = template.projectTo(doImportPerson(id, new Actor(id)), Actor.class);
                        actor.playedIn(movie, (String) entry.get("character"));
                        LOG.info("{} {}", job, actor);
                        actorRepository.save(actor);
                        max ++;
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

        Map data = client.getPerson(personId);
        return data;
    }

    @Transactional
    public void cleanDb() {
        // TODO
        //new Neo4jDatabaseCleaner(template).cleanDb();
    }
}
