CREATE TABLE traveller
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(200) NOT NULL,
    email        VARCHAR(200) NOT NULL,
    created_date TIMESTAMP    NOT NULL,
    deleted_date TIMESTAMP
);

CREATE TABLE account
(
    id             SERIAL PRIMARY KEY,
    traveller_id   INT              NOT NULL,
    balance        DOUBLE PRECISION NOT NULL,
    suspended_date TIMESTAMP,
    CONSTRAINT fk_account_traveller_id FOREIGN KEY (traveller_id) REFERENCES traveller (id)
);

CREATE TABLE ticket
(
    id           SERIAL PRIMARY KEY,
    traveller_id INT              NOT NULL,
    from_town    VARCHAR(100)     NOT NULL,
    to_town      VARCHAR(100)     NOT NULL,
    price        DOUBLE PRECISION NOT NULL,
    created_date TIMESTAMP        NOT NULL,
    expired_date TIMESTAMP        NOT NULL,
    CONSTRAINT fk_ticket_traveller_id FOREIGN KEY (traveller_id) REFERENCES traveller (id)
);

CREATE TABLE route
(
    id        SERIAL PRIMARY KEY,
    from_town VARCHAR(100) NOT NULL,
    to_town   VARCHAR(100) NOT NULL,
    segments  INT          NOT NULL
)