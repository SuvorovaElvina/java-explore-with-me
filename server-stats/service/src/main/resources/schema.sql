
CREATE TABLE IF NOT EXISTS hits (
  id    bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  app  varchar(255) NOT NULL,
  uri varchar(512) NOT NULL,
  ip varchar(255) NOT NULL,
  time timestamp NOT NULL
);