CREATE TABLE IF NOT EXISTS Post (
    id INT NIT NULL,
    user_id INT NOT NULL,
    title VARCHAR(250) NOT NULL,
    body text NOT NULL,
    version int,
    PRIMARY KEY(id)
);