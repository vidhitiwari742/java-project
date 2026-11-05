-- Movie Matrix database schema
-- Run with: mysql -u root -p < schema.sql

DROP DATABASE IF EXISTS movie_matrix;
CREATE DATABASE movie_matrix;
USE movie_matrix;

-- ==================================================
-- Table: users
-- ==================================================
CREATE TABLE users (
    user_id       INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100)        NOT NULL,
    email         VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(256)        NOT NULL,
    role          ENUM('USER', 'ADMIN') DEFAULT 'USER',
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==================================================
-- Table: movies
-- ==================================================
CREATE TABLE movies (
    movie_id      INT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(200)  NOT NULL,
    genre         VARCHAR(50)   NOT NULL,
    release_year  INT           NOT NULL,
    director      VARCHAR(150),
    rating        DECIMAL(3,1)  DEFAULT 0.0,
    description   TEXT,
    poster_url    VARCHAR(500)
);

-- ==================================================
-- Table: user_preferences (favorite genres per user)
-- ==================================================
CREATE TABLE user_preferences (
    preference_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id       INT NOT NULL,
    genre         VARCHAR(50) NOT NULL,
    weight        INT DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE KEY uq_user_genre (user_id, genre)
);

-- ==================================================
-- Table: watch_history
-- ==================================================
CREATE TABLE watch_history (
    history_id   INT AUTO_INCREMENT PRIMARY KEY,
    user_id      INT NOT NULL,
    movie_id     INT NOT NULL,
    watched_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id) ON DELETE CASCADE
);

-- ==================================================
-- Table: reviews
-- ==================================================
CREATE TABLE reviews (
    review_id    INT AUTO_INCREMENT PRIMARY KEY,
    user_id      INT NOT NULL,
    movie_id     INT NOT NULL,
    rating       DECIMAL(3,1) NOT NULL CHECK (rating BETWEEN 0 AND 10),
    comment      TEXT,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id) ON DELETE CASCADE
);

-- ==================================================
-- Sample data
-- ==================================================
INSERT INTO movies (title, genre, release_year, director, rating, description, poster_url) VALUES
('Inception', 'Sci-Fi', 2010, 'Christopher Nolan', 8.8, 'A thief who steals corporate secrets through dream-sharing technology.', ''),
('The Dark Knight', 'Action', 2008, 'Christopher Nolan', 9.0, 'Batman faces the Joker, a criminal mastermind.', ''),
('Interstellar', 'Sci-Fi', 2014, 'Christopher Nolan', 8.6, 'Explorers travel through a wormhole in search of a new home for humanity.', ''),
('The Shawshank Redemption', 'Drama', 1994, 'Frank Darabont', 9.3, 'Two imprisoned men bond over years, finding solace and redemption.', ''),
('Parasite', 'Thriller', 2019, 'Bong Joon-ho', 8.6, 'Greed and class discrimination threaten a newly formed symbiotic relationship.', ''),
('La La Land', 'Romance', 2016, 'Damien Chazelle', 8.0, 'A jazz pianist and an aspiring actress fall in love in Los Angeles.', ''),
('Get Out', 'Horror', 2017, 'Jordan Peele', 7.7, 'A young African-American visits his white girlfriend\'s family estate.', ''),
('The Hangover', 'Comedy', 2009, 'Todd Phillips', 7.7, 'Three friends search for their missing groom-to-be before his wedding.', ''),
('Toy Story', 'Animation', 1995, 'John Lasseter', 8.3, 'A cowboy doll is threatened when a space ranger action figure arrives.', ''),
('The Matrix', 'Sci-Fi', 1999, 'Lana Wachowski', 8.7, 'A hacker learns the true nature of his reality.', ''),
('Gladiator', 'Action', 2000, 'Ridley Scott', 8.5, 'A betrayed Roman general seeks revenge against the corrupt emperor.', ''),
('Titanic', 'Romance', 1997, 'James Cameron', 7.9, 'A love story aboard the ill-fated R.M.S. Titanic.', '');

INSERT INTO users (name, email, password_hash, role) VALUES
-- password for both demo users is: Password123  (SHA-256 hash below, see PasswordUtil.hash)
('Admin User', 'admin@moviematrix.com', '008c70392e3abfbd0fa47bbc2ed96aa99bd49e159727fcba0f2e6abeb3a9d601', 'ADMIN'),
('Demo User', 'demo@moviematrix.com', '008c70392e3abfbd0fa47bbc2ed96aa99bd49e159727fcba0f2e6abeb3a9d601', 'USER');
