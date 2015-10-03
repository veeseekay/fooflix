package com.bar.fooflix.entities;

import com.bar.fooflix.domain.CastData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonTests {

    private static final Logger LOG = LoggerFactory.getLogger(JsonTests.class);

    @Test
    public void userDeSerializationTrueTest() throws Exception {

        String request = "[\n" +
                "  {\n" +
                "    \"login\": \"mickey\",\n" +
                "    \"name\": \"Mickey Mouse\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"login\": \"goofy\",\n" +
                "    \"name\": \"Goofy\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"login\": \"donald\",\n" +
                "    \"name\": \"Donald Duck\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"login\": \"harry\",\n" +
                "    \"name\": \"Harry Potter\"\n" +
                "  }\n" +
                "]";
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.readValue(request,  new TypeReference<List<User>>(){});
        LOG.info("{} in json test", users.size());

        Assert.assertEquals(users.get(0).getLogin(), "mickey");
    }

    @Test
    public void moviesMapDataDeSerializationTrueTest() throws Exception {

        InputStream in = this.getClass().getResourceAsStream("/jsons/movies_data.json");
        String request = IOUtils.toString(in, "UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        List<Map> movieData = mapper.readValue(request,  new TypeReference<List<Map>>(){});
        LOG.info("{} in json test", movieData.size());
        Assert.assertEquals(movieData.size(), 2);

    }

    @Test
    public void moviesDeSerializationTrueTest() throws Exception {

        InputStream in = this.getClass().getResourceAsStream("/jsons/movies_update.json");
        String request = IOUtils.toString(in, "UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        List<Movie> movieData = mapper.readValue(request,  new TypeReference<List<Movie>>(){});
        LOG.info("{} in json test", movieData.size());
    }

    @Test
    public void castDataUpdateDeserTest() throws Exception {

        InputStream in = this.getClass().getResourceAsStream("/jsons/cast_data_update.json");
        String request = IOUtils.toString(in, "UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        List<Actor> cast = mapper.readValue(request,  new TypeReference<List<Actor>>(){});
        LOG.info("{} {} in json test", cast, cast.size(), cast.get(0).getBirthday(), cast.get(0).getBirthplace());
        LOG.info("roles {}", cast.get(0).getRoles().iterator().next().getName());
        assertThat(cast.size(), is(1));
        assertThat(cast.get(0).getBirthday().toString(), containsString("1933"));
        assertThat(cast.get(0).getBirthplace(), containsString("Bangalore"));
    }

    @Test
    public void castDataSerializationTest()
            throws Exception {
        CastData castData = new CastData();
        castData.setMovie(new Movie("1", "Title me"));

        castData.setCast(new ArrayList<Actor>() {{
            Actor actor = new Actor("3", "An actor");
            actor.setBirthplace("Bangalore");
            actor.setRoles(new ArrayList<Role>() {{
                add(new Role(null, null, "Paul Goddard"));
            }});
            add(actor);
        }});

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(castData);

        LOG.info(result);
    }

    @Test
    public void reviewSerializationTest()
            throws Exception {
        List<Review> reviews = new ArrayList<Review>() {{
            add(new Review("thumbs-up", "This is a pleasant movie"));
        }};

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(reviews);

        LOG.info(result);
    }

    @Test
    public void ratingSerializationTest()
            throws Exception {
        List<Rating> ratings = new ArrayList<Rating>() {{
            add(new Rating(2, "Waste of time"));
        }};

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(ratings);

        LOG.info(result);
    }
}
