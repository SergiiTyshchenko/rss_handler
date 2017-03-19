DROP TABLE requests IF EXISTS;
DROP TABLE channel IF EXISTS;

CREATE TABLE requests (
  id         INTEGER PRIMARY KEY,
  requestor VARCHAR(50),
  description VARCHAR(300),
  email  VARCHAR(50),
  assignee VARCHAR(50),
  status VARCHAR(30),
  priority INTEGER
);

CREATE TABLE channel (
    id         INTEGER PRIMARY KEY,
    user VARCHAR(300),
    title VARCHAR(300),
    description VARCHAR(300),
    link VARCHAR(100),
    language  VARCHAR(30),
    pubDate VARCHAR(100),
    lastBuildDate VARCHAR(100),
    items VARCHAR(300)
);



