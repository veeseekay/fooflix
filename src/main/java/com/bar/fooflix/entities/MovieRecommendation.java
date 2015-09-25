package com.bar.fooflix.entities;

import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@MapResult
public interface MovieRecommendation {
    @ResultColumn("otherMovie")
    Movie getMovie();

    @ResultColumn("rating")
    int getRating();
}
