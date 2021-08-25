DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS answer;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS form;
DROP TABLE IF EXISTS "user";

CREATE TABLE "user"
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    username   VARCHAR(255) UNIQUE   NOT NULL,
    password   VARCHAR(255)          NOT NULL,
    first_name VARCHAR(255)          NOT NULL,
    last_name  VARCHAR(255)          NOT NULL,
    created    TIMESTAMP             NOT NULL DEFAULT NOW(),
    updated    TIMESTAMP             NOT NULL DEFAULT NOW()
);

CREATE TABLE form
(
    id              BIGSERIAL PRIMARY KEY NOT NULL,
    title           VARCHAR(255) UNIQUE   NOT NUll,
    description     VARCHAR(255) UNIQUE   NOT NUll,
    question_amount BIGINT                NOT NULL,
    score           DOUBLE PRECISION      NOT NULL,
    user_id         BIGINT REFERENCES "user" (id),
    created         TIMESTAMP             NOT NULL DEFAULT NOW(),
    updated         TIMESTAMP             NOT NULL DEFAULT NOW()
);

CREATE TABLE question
(
    id       BIGSERIAL PRIMARY KEY NOT NULL,
    question VARCHAR(255) UNIQUE   NOT NULL,
    form_id  BIGINT REFERENCES form (id)
);

CREATE TABLE answer
(
    id          BIGSERIAL PRIMARY KEY NOT NULL,
    answer      VARCHAR(255) UNIQUE   NOT NULL,
    question_id BIGINT REFERENCES question (id)
);

CREATE TABLE role
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) UNIQUE   NOT NULL
);

CREATE TABLE user_role
(
    user_id BIGINT NOT NULL REFERENCES "user" (id),
    role_id BIGINT NOT NULL REFERENCES role (id)
);

INSERT INTO "user"(username, password, first_name, last_name)
VALUES ('ad', '{bcrypt}$2a$10$BiSKmQTLji.77ucjVukD7uQnoSHoTpJHPG34DJWGszylFmMCkjlDi', 'Admin', 'Admin'),
       ('simp', '{bcrypt}$2a$10$BiSKmQTLji.77ucjVukD7uQnoSHoTpJHPG34DJWGszylFmMCkjlDi', 'Simple', 'Simple');

INSERT INTO form (title, description, question_amount, score, user_id)
VALUES ('Test', 'testtesttest', 666, 10, 1),
       ('Test2', 'testtesttest2', 333, 44, 2);

INSERT INTO question (question, form_id)
VALUES ('Yes?', 1),
       ('Yes', 2);

INSERT INTO answer (answer, question_id)
VALUES ('No', 1),
       ('No4', 2);

INSERT INTO role (name)
VALUES ('USER'),
       ('ADMIN');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (2, 1);