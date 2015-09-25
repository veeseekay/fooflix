package com.bar.fooflix.repositories;

import com.bar.fooflix.entities.Movie;
import com.bar.fooflix.entities.MovieRecommendation;
import com.bar.fooflix.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import java.util.List;

public interface MovieRepository extends GraphRepository<Movie>,
        NamedIndexRepository<Movie>,
        RelationshipOperationsRepository<Movie> {
    Movie findById(String id);

    Page<Movie> findByTitleLike(String title, Pageable page);

    @Query( "start user=node({0}) " +
            " match user-[r:RATED]->movie<-[r2:RATED]-other-[r3:RATED]->otherMovie " +
            " where r.stars >= 3 and r2.stars >= r.stars and r3.stars >= r.stars " +
            " return otherMovie, avg(r3.stars) as rating, count(*) as cnt" +
            " order by rating desc, cnt desc" +
            " limit 10" )
    List<MovieRecommendation> getRecommendations( User user );

}