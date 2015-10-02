package com.bar.fooflix.tests;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.graphdb.Direction;
import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NodeEntity
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
@JsonPropertyOrder({
        "id", "title", "description", "directors"
})
public class Movie {
    @GraphId
    Long nodeId;

    @JsonProperty("id")
    @Indexed(unique = true)
    String id;

    @JsonProperty("title")
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "search")
    String title;

    @JsonIgnore
    @JsonProperty("description")
    String description;

    //@JsonManagedReference(value = "has_movie")
    @JsonProperty("genres")
    @RelatedTo(type = "HAS_MOVIE", direction = Direction.BOTH)
    @Fetch
    Set<Genre> genres;

    //@JsonManagedReference(value = "acts_in")
    @JsonProperty("actors")
    @RelatedTo(type = "ACTS_IN", direction = Direction.INCOMING)
    Set<Person> actors;

    @JsonManagedReference(value = "has_role")
    @RelatedToVia(type = "ACTS_IN", direction = Direction.INCOMING)
    Collection<Role> roles;

    @RelatedToVia(type = "RATED", direction = Direction.INCOMING)
    @Fetch
    List<Rating> ratings;

    @RelatedTo(type = "HAS_REVIEW", direction = Direction.INCOMING)
    @Fetch
    List<Review> reviews;

    //@JsonManagedReference(value = "directed")
    @JsonProperty("directors")
    @RelatedTo(type = "DIRECTED", direction = Direction.INCOMING)
    @Fetch
    Set<Person> directors;

    private String language;
    private String imdbId;
    private String tagline;
    private Date releaseDate;
    private Integer runtime;
    private String homepage;
    private String trailer;
    private String genre;
    private String studio;
    private Integer version;
    private Date lastModified;
    private String imageUrl;

    public Movie() {
    }

    public Movie(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Collection<Person> getActors() {
        return actors;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public int getYear() {
        if (releaseDate == null) return 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(releaseDate);
        return cal.get(Calendar.YEAR);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStars() {
        Iterable<Rating> allRatings = ratings;

        if (allRatings == null) return 0;
        int stars = 0, count = 0;
        for (Rating rating : allRatings) {
            stars += rating.getStars();
            count++;
        }
        return count == 0 ? 0 : stars / count;
    }

    public Collection<Rating> getRatings() {
        Iterable<Rating> allRatings = ratings;
        return allRatings == null ? Collections.<Rating>emptyList() : IteratorUtil.asCollection(allRatings);
    }

    public Collection<Review> getReviews() {
        Iterable<Review> allReviews = reviews;
        return allReviews == null ? Collections.<Review>emptyList() : IteratorUtil.asCollection(allReviews);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getYoutubeId() {
        String trailerUrl = trailer;
        if (trailerUrl == null || !trailerUrl.contains("youtu")) return null;
        String[] parts = trailerUrl.split("[=/]");
        int numberOfParts = parts.length;
        return numberOfParts > 0 ? parts[numberOfParts - 1] : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;
        if (nodeId == null) return super.equals(o);
        return nodeId.equals(movie.nodeId);

    }

    @Override
    public int hashCode() {
        return nodeId != null ? nodeId.hashCode() : super.hashCode();
    }

   // @JsonManagedReference(value = "directors")
    public Set<Person> getDirectors() {
        return directors;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) [%s]", title, releaseDate, id);
    }
}
