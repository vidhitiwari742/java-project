# Movie Matrix

Movie Matrix is an AI-powered movie recommendation platform that suggests personalized films
based on user interests, preferences, and viewing history.

## Features

- **AI-style Recommendations** — a custom hybrid recommendation engine (genre-preference weighting
  blended with movie rating) built from each user's watch history and reviews. No external API key
  required; see `service/HybridRecommendationStrategy`.
- **Movie Catalog** — search and filter movies by title/genre.
- **User Preference Tracking** — preferences are derived dynamically from watched movies and ratings.
- **Two front ends**:
  - A web app built with **Servlets + JSP** (JSTL, session-based auth).
  - A desktop **Swing GUI** client that talks to the same service/DAO layer directly over JDBC.
- **Reviews & ratings** — users can rate and review movies; a movie's average rating updates automatically.

## Tech Stack

- Language: Java 17
- Database: MySQL 8 (JDBC via `mysql-connector-j`)
- Web: Jakarta Servlets 6 / JSP / JSTL (tested against Apache Tomcat 10.1+)
- Desktop GUI: Swing
- Build: Maven
- Tests: JUnit 5

Note- Apache Tomcat is an open-source web server that runs Java-based web applications. It takes compiled Java Servlets(A Java program that runs on a web server (like Tomcat)) and JSPs(codes made up of HTML and Java languages), hosts them, and shows them as a live website that users can open in a browser. Apache Maven is a manager which allows us to search for and download libraries, files, etc., in an easier way. 

## Project Structure

```
java-project/
├── database/schema.sql                # DB schema + sample data
├── pom.xml
├── src/main/java/com/moviematrix/
│   ├── model/      # Person, User, Admin, Movie, Review, WatchHistoryEntry, Genre (OOP domain model)
│   ├── dao/        # JDBC data-access layer (DBConnection + *DAO / *DAOImpl)
│   ├── service/     # Business logic: UserService, MovieService, RecommendationEngine + strategies
│   ├── servlet/     # Web integration layer (Login/Register/MovieList/MovieDetails/Recommendation)
│   └── gui/         # MovieMatrixSwingApp — standalone desktop client
├── src/main/resources/db.properties    # DB connection config
├── src/main/webapp/                    # JSP pages + CSS
└── src/test/java/com/moviematrix/      # JUnit test cases
```

## Prerequisites

- JDK 17 or later
- Maven 3.8+
- MySQL Server & MySQL Workbench (or any MySQL client)
- Apache Tomcat 10.1+ (for the web app — needs a Jakarta EE 10 compatible container)
- IDE (Eclipse, IntelliJ IDEA, or NetBeans) — optional

## Setup & Installation

### 1. Clone the repository

```
git clone https://github.com/vidhitiwari742/java-project.git
cd java-project
```

### 2. Create the database

```
mysql -u root -p < database/schema.sql
```

This creates the `movie_matrix` database with `users`, `movies`, `reviews`, `watch_history`, and
`user_preferences` tables, plus sample movies and a demo account.

**Demo login:** `demo@moviematrix.com` / `Password123`

### 3. Configure DB credentials

Edit `src/main/resources/db.properties` if your MySQL user/password differ from the defaults:

```properties
db.url=jdbc:mysql://localhost:3306/movie_matrix?useSSL=false&serverTimezone=UTC
db.user=root
db.password=root
```

### 4. Build the project

```
mvn clean package
```

This compiles the code, runs the JUnit test suite, and produces `target/movie-matrix.war`.

### 5. Run the web app

Deploy `target/movie-matrix.war` to Tomcat (e.g. copy it into Tomcat's `webapps/` folder), then
start Tomcat and open:

```
http://localhost:8080/movie-matrix/
```

### 6. Run the desktop (Swing) app

```
mvn compile exec:java -Dexec.mainClass="com.moviematrix.gui.MovieMatrixSwingApp"
```

(or run `com.moviematrix.gui.MovieMatrixSwingApp.main()` directly from your IDE). It connects to
the same MySQL database using the same `db.properties`.

## Running Tests

```
mvn test
```

Test cases cover (no live database required — DAOs are abstracted behind interfaces so tests use
in-memory fakes):

- `PasswordUtilTest` — password hashing correctness
- `MovieTest` — `Movie` natural ordering (`Comparable`) and equality, `Genre` parsing
- `RecommendationStrategyTest` — genre-based, rating-based and hybrid recommendation algorithms
- `UserServiceTest` — registration validation, duplicate-email rejection, authentication

## Core Java Concepts Demonstrated

- **OOP**: abstraction (`Person` abstract class), inheritance (`User`/`Admin` extend `Person`),
  polymorphism (`RecommendationStrategy` implementations, `getRole()` overrides), encapsulation
  (private fields + getters/setters across all model classes).
- **Interfaces & Strategy pattern**: `RecommendationStrategy`, `MovieDAO`, `UserDAO`, `ReviewDAO`,
  `WatchHistoryDAO` separate contracts from JDBC implementations.
- **JDBC**: `DBConnection` + `*DAOImpl` classes use `PreparedStatement` for all queries (SQL
  injection-safe), with proper `try-with-resources` connection handling.
- **Web integration**: Servlets handle HTTP request/response and session-based auth; JSP + JSTL
  render views; no business logic lives in the JSP layer.
- **GUI**: a Swing desktop client reusing the exact same service/DAO layer as the web app.
- Application Workflow

The Movie Matrix application follows a simple workflow:

1. User Registration/Login: Users create an account or log in to access the platform.
2. Movie Exploration: Users browse the movie catalog and search or filter movies by title and genre.
3. Movie Interaction: Users watch movies and submit ratings and reviews.
4. Preference Tracking: The system uses viewing history, genres, and user ratings to understand individual preferences.
5. Personalized Recommendations: The hybrid recommendation engine analyzes user preferences and movie ratings to suggest relevant movies.

How the Recommendation Engine Works

Movie Matrix uses a custom hybrid recommendation strategy instead of relying on an external AI API.

The recommendation engine combines:

- Genre-based recommendations: Identifies genres that match the user's interests.
- Rating-based recommendations: Considers movie ratings to help identify highly rated films.
- Hybrid approach: Combines genre preference weighting and movie ratings to generate personalized recommendations.

This approach provides recommendations based on user activity and preferences.

Future Enhancements

Possible improvements for future versions include:

- Adding movie posters and trailers.
- Improving recommendation accuracy using more user interaction data.
- Adding advanced movie filters and sorting options.
- Enhancing the user interface and overall experience.
- Introducing additional recommendation techniques.

- ## Challenges & Solutions

| Challenge | Solution |
|---|---|
| Generating personalized recommendations | Implemented a hybrid recommendation strategy |
| Managing database operations | Used DAO interfaces and JDBC implementations |
| Protecting user passwords | Implemented password hashing |
| Maintaining code organization | Followed layered architecture |
| Supporting multiple interfaces | Reused the same service and DAO layers |


