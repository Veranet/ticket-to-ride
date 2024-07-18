CREATE TABLE traveller (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(200) NOT NULL,
                           email VARCHAR(200) NOT NULL,
                           created_date TIMESTAMP NOT NULL,
                           delete_date BOOl DEFAULT TRUE
);

CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    traveller_id INT NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_traveller_id FOREIGN KEY (traveller_id) REFERENCES traveller (id)
);



CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    from_town VARCHAR(100) NOT NULL,
    to_town VARCHAR(100) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    created_date TIMESTAMP NOT NULL
);

CREATE TABLE account_ticket (
    ticket_id INT NOT NULL,
    account_id INT NOT NULL,
    CONSTRAINT fk_ticket_id FOREIGN KEY (ticket_id) REFERENCES ticket (id),
    CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE TABLE route (
    id SERIAL PRIMARY KEY,
    from_town VARCHAR(100) NOT NULL,
    to_town VARCHAR(100) NOT NULL,
    segments INT NOT NULL
)