# --- !Ups

CREATE TABLE PEOPLE (
  `ID` INT AUTO_INCREMENT PRIMARY KEY,
  `NAME` VARCHAR(255) NOT NULL,
  `EMAIL` VARCHAR(255) NOT NULL,
  `TEL` VARCHAR(255)
);
insert into people (name, email, tel) values ('John Doe', 'john@doe.com', '1234567890');
insert into people (name, email, tel) values ('Jane Doe', 'jane@doe.com', '1234567890');
insert into people (name, email, tel) values ('John Smith', 'john@smith.com', '1234567890');

# --- !Downs

DROP TABLE people;

