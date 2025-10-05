CREATE DATABASE IF NOT EXISTS movies;
USE movies;

-- Users
CREATE TABLE IF NOT EXISTS users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(64) NOT NULL UNIQUE
);
 
-- Movies
CREATE TABLE IF NOT EXISTS movies ( 
  id INT PRIMARY KEY AUTO_INCREMENT ,
  title VARCHAR(255) NOT NULL
);

-- Ratings (1..5)
CREATE TABLE IF NOT EXISTS ratings (
  user_id INT NOT NULL,
  movie_id INT NOT NULL,
  rating DOUBLE NOT NULL,
  PRIMARY KEY (user_id, movie_id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (movie_id) REFERENCES movies(id)
);

-- Seed users
INSERT IGNORE INTO users (id, username) VALUES
  (1,'alice'),(2,'bob'),(3,'carol');

-- Seed movies
INSERT IGNORE INTO movies (id, title) VALUES
  (1,'Inception'),(2,'The Matrix'),(3,'Interstellar'),(4,'Toy Story'),(5,'The Dark Knight');

-- Seed ratings
INSERT IGNORE INTO ratings (user_id, movie_id, rating) VALUES
  (1,1,5),(1,2,4),(1,3,5),
  (2,1,4),(2,2,5),(2,4,3),
  (3,2,4),(3,3,4),(3,5,5);
