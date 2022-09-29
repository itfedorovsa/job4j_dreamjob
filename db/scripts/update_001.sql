CREATE TABLE IF NOT EXISTS post (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255),
   description VARCHAR(255),
   created TIMESTAMP WITHOUT TIME ZONE,
   visible BOOLEAN,
   city_id INT
);

CREATE TABLE IF NOT EXISTS candidates (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255),
   description VARCHAR(255),
   created TIMESTAMP WITHOUT TIME ZONE,
   visible BOOLEAN,
   city_id INT,
   photo BYTEA
);

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255),
  email VARCHAR(255) UNIQUE,
  password VARCHAR(255)
);
