package com.bar.fooflix.tests;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.List;

/**
 * Created by z013w8c on 10/2/15.
 */

public class Tester {

    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
    public static class User
    {
        private int id;
        private List<Event> events;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }

        // Getters and setters
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
    public static class Event
    {
        private int id;
        private List<User> users;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }
    }

    public void moviesDeSerializationTrueTest() throws Exception {

        InputStream in = this.getClass().getResourceAsStream("/jsons/movies_update.json");
        String request = IOUtils.toString(in, "UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        List<Movie> movieData = mapper.readValue(request,  new TypeReference<List<Movie>>(){});
    }

    public static void main(String [] args) throws Exception {
        /*ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        Event event1 = new Event();
        event1.setId(1);
        Event event2 = new Event();
        event2.setId(2);

        User user = new User();
        user.setId(10);

        event1.setUsers(Arrays.asList(user));
        event2.setUsers(Arrays.asList(user));
        user.setEvents(Arrays.asList(event1, event2));

        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);

        User deserializedUser = objectMapper.readValue(json, User.class);
        System.out.println(deserializedUser);*/

        Tester t = new Tester();
        t.moviesDeSerializationTrueTest();
    }
}
