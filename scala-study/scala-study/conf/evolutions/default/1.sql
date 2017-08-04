# Tasks schema
 
# --- !Ups

CREATE TABLE task (
    id integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    label varchar(255)
);
 
# --- !Downs
 
DROP TABLE task;