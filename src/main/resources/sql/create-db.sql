DROP TABLE requests IF EXISTS;

CREATE TABLE requests (
  id         INTEGER PRIMARY KEY,
  requestor VARCHAR(30),
  description VARCHAR(30),
  email  VARCHAR(50),
  assignee VARCHAR(30),
  status VARCHAR(30),
  priority INTEGER
);