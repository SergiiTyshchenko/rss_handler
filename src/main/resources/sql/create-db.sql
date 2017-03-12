DROP TABLE requests IF EXISTS;
DROP TABLE channel IF EXISTS;

CREATE TABLE requests (
  id         INTEGER PRIMARY KEY,
  requestor VARCHAR(30),
  description VARCHAR(30),
  email  VARCHAR(50),
  assignee VARCHAR(30),
  status VARCHAR(30),
  priority INTEGER
);

CREATE TABLE channel (
    id         INTEGER PRIMARY KEY,
    title VARCHAR(300),
    link VARCHAR(100),
    description VARCHAR(300),
    language  VARCHAR(30),
    pubDate VARCHAR(100),
    dc_date VARCHAR(100),
    dc_language VARCHAR(30),
    item VARCHAR(300)
);


