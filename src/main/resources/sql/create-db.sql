DROP TABLE channel IF EXISTS;
DROP TABLE item IF EXISTS;

CREATE TABLE channel (
    id         UUID PRIMARY KEY,
    shortid         INTEGER,
    user VARCHAR(300),
    title VARCHAR(300),
    description VARCHAR(300),
    link VARCHAR(500),
    language  VARCHAR(30),
    pubDate TIMESTAMP,
    lastBuildDate TIMESTAMP,
    items INTEGER
);

CREATE TABLE item (
    id         UUID PRIMARY KEY,
    channelID  INTEGER,
    title VARCHAR(300),
    description CLOB,
    link VARCHAR(500),
    pubDate VARCHAR(100)
);

 ALTER TABLE item ADD FOREIGN KEY ( channelID ) REFERENCES channel( shortid ) ;




