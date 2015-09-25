package com.bar.fooflix.utils;


import com.bar.fooflix.entities.Movie;
import com.bar.fooflix.entities.Person;
import com.bar.fooflix.entities.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

// Get some static stuff done here
public class TmdbJsonMapper {

    private static final Logger LOG = LoggerFactory.getLogger(TmdbJsonMapper.class);

    public static void mapToMovie(Map data, Movie movie) {
        try {
            movie.setTitle((String) data.get("title"));
            movie.setLanguage((String) data.get("original_language"));
            movie.setImdbId((String) data.get("imdb_id"));
            movie.setTagline((String) data.get("tagline"));
            movie.setDescription(limit((String) data.get("overview"), 500));
            movie.setReleaseDate(toDate(data, "release_date", "yyyy-MM-dd"));
            movie.setRuntime((Integer) data.get("runtime"));
            movie.setHomepage((String) data.get("homepage"));
            Map trailer = extractFirstMap(data, "trailers", "youtube");
            if (trailer != null) movie.setTrailer("http:/youtu.be/" + trailer.get("source"));
            movie.setGenre(extractFirst(data, "genres", "name"));
            movie.setStudio(extractFirst(data, "production_companies", "name"));
            //movie.setImageUrl(String.format(posterFormat, data.get("poster_path")));
        } catch (Exception e) {
            LOG.error("could not map movie", e);
        }
    }

    private static String selectImageUrl(List<Map> data, final String type, final String size) {
        if (data == null) return null;
        for (Map entry : data) {
            Map image = (Map) entry.get("image");
            if (image.get("type").equals(type) && image.get("size").equals(size)) return (String) image.get("url");
        }
        return null;
    }


    private static String extractFirst(Map data, String field, String property) {
        List<Map> inner = (List<Map>) data.get(field);
        if (inner == null || inner.isEmpty()) return null;
        return (String) inner.get(0).get(property);
    }

    private static Map extractFirstMap(Map data, String field, String property) {
        Map inner = (Map) data.get(field);
        if (inner == null || inner.isEmpty()) return null;
        List list = (List) inner.get(property);
        if (list == null || list.isEmpty()) return null;
        return (Map) list.get(0);
    }

    private static Date toDate(Map data, String field, final String pattern) throws ParseException {
        try {
            String dateString = (String) data.get(field);
            if (dateString == null || dateString.isEmpty()) return null;
            return new SimpleDateFormat(pattern).parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static void mapToPerson(Map data, Person person) {
        try {
            person.setName((String) data.get("name"));
            person.setBirthday(toDate(data, "birthday", "yyyy-MM-dd"));
            person.setBirthplace((String) data.get("place_of_birth"));
            String biography = (String) data.get("biography");
            person.setBiography(limit(biography, 500));
        } catch (Exception e) {
            LOG.error("could not map person", e);
        }
    }

    private static String limit(String text, int limit) {
        if (text == null || text.length() < limit) return text;
        return text.substring(0, limit);
    }

    public static Roles mapToRole(String roleString) {
        if (roleString == null || roleString.equals("Actor")) {
            return Roles.ACTS_IN;
        }
        if (roleString.equals("Director")) {
            return Roles.DIRECTED;
        }
        return null;
    }
}