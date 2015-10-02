package com.bar.fooflix.entities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

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
}
