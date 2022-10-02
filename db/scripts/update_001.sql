CREATE TABLE IF NOT EXISTS post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created TIMESTAMP WITHOUT TIME ZONE,
   visible BOOLEAN,
   city_id INT
);

CREATE TABLE IF NOT EXISTS candidates (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created TIMESTAMP WITHOUT TIME ZONE,
   visible BOOLEAN,
   city_id INT,
   photo BYTEA
);

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name TEXT,
  email TEXT UNIQUE,
  password TEXT
);
