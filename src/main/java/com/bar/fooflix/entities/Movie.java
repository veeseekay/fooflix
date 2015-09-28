package com.bar.fooflix.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
import java.util.Set;

import static org.neo4j.graphdb.Direction.INCOMING;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NodeEntity
@JsonPropertyOrder({
        "id", "title", "description", "directors"
})
public class Movie {
    @GraphId Long nodeId;

    @JsonProperty("id")
    @Indexed(unique = true)
    String id;

    @JsonProperty("title")
    @Indexed(indexType=IndexType.FULLTEXT, indexName = "search")
    String title;

    @JsonIgnore
    @JsonProperty("description")
    String description;

    @JsonIgnore
    @JsonManagedReference
    @JsonProperty("directors")
    @RelatedTo(type="DIRECTED", direction = INCOMING)
    @Fetch
    Set<Person> directors;

    @JsonIgnore
    @JsonManagedReference
    @JsonProperty("actors")
    @RelatedTo(type = "ACTS_IN", direction = INCOMING)
    Set<Person> actors;

    @RelatedToVia(type = "ACTS_IN", direction = INCOMING)
    Collection<Role> roles;

    @RelatedToVia(type = "RATED", direction = INCOMING)
    @Fetch
    Iterable<Rating> ratings;

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
        if (releaseDate==null) return 0;
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

    public String getDescription() {
        return description;
    }

    public int getStars() {
        Iterable<Rating> allRatings = ratings;

        if (allRatings == null) return 0;
        int stars=0, count=0;
        for (Rating rating : allRatings) {
            stars += rating.getStars();
            count++;
        }
        return count==0 ? 0 : stars / count;
    }

    @JsonIgnore
    public Collection<Rating> getRatings() {
        Iterable<Rating> allRatings = ratings;
        return allRatings == null ? Collections.<Rating>emptyList() : IteratorUtil.asCollection(allRatings);
    }

    public void setTitle(String title) {
        this.title=title;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLanguage() {
        return language;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getTagline() {
        return tagline;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getGenre() {
        return genre;
    }

    public String getStudio() {
        return studio;
    }

    public Integer getVersion() {
        return version;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getYoutubeId() {
        String trailerUrl = trailer;
        if (trailerUrl==null || !trailerUrl.contains("youtu")) return null;
        String[] parts = trailerUrl.split("[=/]");
        int numberOfParts = parts.length;
        return numberOfParts > 0 ? parts[numberOfParts-1] : null;
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

    @JsonIgnore
    public Set<Person> getDirectors() {
        return directors;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) [%s]", title, releaseDate, id);
    }
}
