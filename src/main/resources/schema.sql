CREATE TABLE IF NOT EXISTS Post (
    id INT NOT NULL,
    user_id INT NOT NULL,
    title VARCHAR(250) NOT NULL,
    body text NOT NULL,
    version int,
    PRIMARY KEY(id)
);


CREATE TABLE IF NOT EXISTS course (
    course_id INT NOT NULL,
    title varchar(80) NOT NULL,         -- Course Title
    description varchar(500) NOT NULL,  -- Course Description
    link varchar(255) NOT NULL,         -- Course landing page link
    CONSTRAINT pk_course_course_id PRIMARY KEY (course_id)
);