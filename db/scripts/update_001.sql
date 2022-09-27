CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name varchar(255),
   description varchar(255),
   created timestamp without time zone,
   visible boolean,
   city_id int
);