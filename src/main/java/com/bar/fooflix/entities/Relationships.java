package com.bar.fooflix.entities;

import org.neo4j.graphdb.RelationshipType;

public enum Relationships implements RelationshipType {
    REVIEWS, UPVOTE, DOWNVOTE, HAS_REVIEW, WATCHED, COMMENTS, RATED, BELONGS_TO, RECOMMENDED
}