# Movie Watchlist API

A Spring Boot application to manage watchers, movies, and personalized watchlists.

## Features
- **CRUD** on Watchers, Movies, WatchlistEntries
- **Filtering** supported:
  - **Movies**: by `title`, `genre`, `director`, `releaseYear`
  - **Watchlist Entries**: by `status`, `rating`, `genre`
- Uses **DTOs and converters** for all input/output – entities are never exposed directly
- Returns proper **404 Not Found** responses when resources are missing (e.g., no entries for a watcher)
- **Swagger UI** available at: [http://localhost:8080/swagger-ui/index.html] (http://localhost:8080/swagger-ui/index.html)
- **Postman collection** included in the project root for easy testing: `watchlist-API.postman_collection.json`

## Getting Started

### Prerequisites
- Java 21+
- Maven 3.6+
- MySQL running on `localhost:3306`

### Configuration
Update `src/main/resources/application.properties` with your local DB credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/movie-watchlist?createDatabaseIfNotExist=true
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASS