# fooflix
Foobar recommendation engine

uri | GET | POST | PUT | DELETE
--- | :---: | :----: | :---: | ------:
/movies | paginated movies | add new movies | bulk update | 400 bad request
/movies/{id} | show movie | 400 bad request | update if exists, else 404 not found | 400 bad request
/movies/{id}/cast | show cast | add cast | update cast | 400 bad request
/movies/{id}/crew | show crew | add crew | update crew | 400 bad request
/users/ | paginated users | add new user | bulk update | 400 bad request
/users/{id} | show user | 400 bad request | update if exists, else 404 not found | 400 bad request
/movies/{id}/ratings | paginated ratings by user, aggregate=y for aggregated ratings | add new rating by a user | update if exists, else 404 not found | delete if exists, else 404 not found
/movies/{id}/reviews | paginated reviews by user | add new review by a user | update if exists, else 404 not found | delete if exists, else 404 not found

