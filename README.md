# fooflix
Foobar recommendation engine (http://localhost:8080/fooflix/v1)

uri | GET | POST | PUT | DELETE
--- | :---: | :----: | :---: | ------:
/movies | paginated movies | add new movies | bulk update (only movie title can be changed) | 400 bad request
/movies/{id} | show movie | 400 bad request | update if exists, else 404 not found | 400 bad request
/movies/{id}/cast | show cast | add cast | 400 bad request | 400 bad request
/movies/{id}/crew | show crew | add crew | 400 bad request | 400 bad request
/users/ | paginated users | add new user | bulk update (only user name can be changed) | 400 bad request
/users/{id} | show user | 400 bad request | update if exists, else 404 not found | 400 bad request
/movies/{id}/ratings | paginated ratings | add new rating by a user | 404 not found | 404 not found
/movies/{id}/reviews | paginated reviews | add new review by a user | 404 not found | 404 not found
/recommendations/actor | paginated movie reco by actor | 400 bad request | 400 bad request | 400 bad request
/recommendations/genre | paginated movie reco by genre | 400 bad request | 400 bad request | 400 bad request


# Build & Run

* $ git clone https://github.com/veeseekay/fooflix.git
* $ cd fooflix
* $ ./gradlew clean build
* $ ./gradlew bootRun

# Test Application

* Import postman collections
* Run Load Me request to load sample data set
* View movies, user, ratings, reviews, up/down votes created in the neo4j browser
* Start running the postman requests in order
* Run [POST] movie cast and view changes in neo4j browser, you should see "Paul Goddard" added as cast "Agent Brown" for movie id 603
* Run [POST] movie crew and view changes in neo4j browser, you should see 2 additional directors for movie id 603
* Run the get paginated, update and create movie requests. Ensure you send the right movie "id" in the request body for update
* Run the get paginated, update and create user requests. Ensure you send the right user "id" in the request body for update
* Play around with rest of the requests for reviews and ratings

# The Stack
These are the components of our Restful Application:
* Application Type: Spring-Boot Java Web Application (Jetty)
* Persistence Access: Spring-Data-Neo4j
* Database viewer : Neo4j-Browser (http://localhost:8686, user : neo4j, pass : password)
* Database: Embedded Neo4j-Server
* Uses movie data from www.themoviedb.org

# Postman collection
* https://www.getpostman.com/collections/3affe9982ffadc09d56c

# Known Issues
* Exception seen when accessing neo4j browser
java.lang.IllegalStateException: The Neo Server running is of unknown type. Valid types are Community, Advanced, and Enterprise.
This does not affect the code and can be ignored
* neo4j browser does not show up when run as an uber jar - fixed

# TODO
* Error scenarios
* Upvotes and Downvote apis to be implemented
* Unit tests for code coverage
* Functional tests for stability
* Consolidate string constants in code
* Swagger for api-docs