# --- !Ups

CREATE TABLE people (
  `id` int AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `tel` varchar(255),
);
insert into people (name, email, tel) values ('John Doe', 'john@doe.com', '1234567890');
insert into people (name, email, tel) values ('Jane Doe', 'jane@doe.com', '1234567890');
insert into people (name, email, tel) values ('John Smith', 'john@smith.com', '1234567890');

# --- !Downs

DROP TABLE people;

