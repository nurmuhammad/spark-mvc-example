CREATE TABLE users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  email varchar NOT NULL,
  password varchar NOT NULL,
  salt varchar,
  status BOOLEAN,
  created INTEGER,
  changed INTEGER,
  PRIMARY KEY (id)
);

INSERT INTO users(email, password, salt, status, created, changed)
    VALUES ('demo@gmail.com', '828bfa9f0ce0e7f3c9c8d6ae8970b090', 'abcc2612-9a2b-47c6-9d9d-cc08c0a468e1', TRUE , 1452542477, 1452542477)