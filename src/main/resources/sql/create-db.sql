DROP TABLE channel IF EXISTS;
DROP TABLE item IF EXISTS;

CREATE TABLE channel (
    id         UUID PRIMARY KEY,
    user VARCHAR(300),
    title VARCHAR(300),
    description VARCHAR(300),
    link VARCHAR(100),
    language  VARCHAR(30),
    pubDate VARCHAR(100),
    lastBuildDate VARCHAR(100),
    items UUID
);

CREATE TABLE item (
    id         UUID PRIMARY KEY,
    channelID  UUID,
    title VARCHAR(300),
    description CLOB,
    link VARCHAR(100),
    pubDate VARCHAR(100)
);

 ALTER TABLE item ADD FOREIGN KEY ( channelID ) REFERENCES channel( id ) ;




