package com.bar.fooflix.services;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class TmdbApiClient {

    @Value("${tmdb.url:http://api.themoviedb.org}")
    String baseUrl;

    @Value("${tmdb.api.key:926d2a79e82920b62f03b1cb57e532e6}")
    String apiKey;

    public Map getMovie(String id) {
        return loadJsonData(buildMovieUrl(id));
    }

    private Map loadJsonData(String url) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            Object value = mapper.readValue(new URL(url), Object.class);
            Map map = null;
            if (value instanceof List) {
                List list= (List) value;
                if (list.isEmpty() || list.get(0).equals("Nothing found."))
                    return notFound();
                map = (Map)list.get(0);
            }
            if (value instanceof Map) {
                map = (Map)value;
            }
            if (map == null ) {
                return notFound();
            }
            if (map.containsKey("status_code")) {
                map.putAll(notFound());
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get data from " + url, e);
        }
    }

    private Map<String, Long> notFound() {
        return Collections.singletonMap("not_found", System.currentTimeMillis());
    }

    private String buildMovieUrl(String movieId) {
        return String.format("%s/3/movie/%s?api_key=%s&append_to_response=credits,trailers", baseUrl, movieId, apiKey);
    }
    private String buildConfigUrl() {
        return String.format("%s/3/configuration?api_key=%s", baseUrl, apiKey);
    }

    public Map getPerson(String id) {
        return loadJsonData(buildPersonUrl(id));
    }

    private String buildPersonUrl(String personId) {
        return String.format("%s/3/person/%s?api_key=%s", baseUrl,  personId, apiKey);
    }
}
