-- DROP DATABASE IF EXISTS UserRest;

-- CREATE DATABASE UserRest;

DROP TABLE IF EXISTS users; 

CREATE TABLE users(
name VARCHAR (50) NOT NULL,
email VARCHAR (30) NOT NULL,
password VARCHAR (30) NOT NULL,
userId SERIAL PRIMARY KEY);

SELECT * FROM users;

