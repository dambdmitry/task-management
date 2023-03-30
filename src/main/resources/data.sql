DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS users;

CREATE TABLE users(
    id int NOT NULL ,
    username varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE tasks(
    id int NOT NULL ,
    code varchar(255),
    name varchar(255),
    author_id int NOT NULL ,
    executor_id int,
    status varchar(15),
    time_to_execute date,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (executor_id) REFERENCES users(id)
);


