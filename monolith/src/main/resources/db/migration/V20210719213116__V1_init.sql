USE flight_atdd;
CREATE TABLE user
(
    username  VARCHAR(255) PRIMARY KEY,
    firstname VARCHAR(255),
    lastname  VARCHAR(255),
    password  VARCHAR(255),
    enabled   BOOLEAN
);

CREATE TABLE role
(
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE user_role
(
    username  VARCHAR(255) NOT NULL,
    role_name VARCHAR(50)  NOT NULL,
    PRIMARY KEY (username, role_name),
    INDEX     fk_user_role_role_idx (role_name ASC),
    CONSTRAINT fk_user_role_user
        FOREIGN KEY (username)
            REFERENCES user (username),
    CONSTRAINT fk_user_role_role
        FOREIGN KEY (role_name)
            REFERENCES role (name)
);

CREATE TABLE country
(
    id   INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE city
(
    id          INTEGER AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100),
    description VARCHAR(255),
    country_id  INTEGER,
    INDEX       country_index (country_id),
    FOREIGN KEY (country_id)
        REFERENCES country (id)
        ON DELETE CASCADE
);

CREATE TABLE comment
(
    id       INTEGER AUTO_INCREMENT PRIMARY KEY,
    details  VARCHAR(255),
    created  DATETIME,
    modified DATETIME,
    city_id  INTEGER,
    INDEX    city_index (city_id),
    FOREIGN KEY (city_id)
        REFERENCES city (id)
        ON DELETE CASCADE
);

CREATE TABLE airport
(
    id         INTEGER AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255),
    iata       VARCHAR(3),
    icao       VARCHAR(4),
    latitude   DOUBLE,
    longitude  DOUBLE,
    altitude   INTEGER,
    timezone   VARCHAR(50),
    DST        VARCHAR(50),
    tz         VARCHAR(50),
    type       VARCHAR(50),
    source     VARCHAR(50),
    city_id    INTEGER,
    country_id INTEGER,
    INDEX      city_index (city_id),
    INDEX      country_index (country_id),
    FOREIGN KEY (city_id)
        REFERENCES city (id)
        ON DELETE CASCADE,
    FOREIGN KEY (country_id)
        REFERENCES country (id)
        ON DELETE CASCADE
);

CREATE TABLE airline
(
    id   INTEGER AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(3) NOT NULL UNIQUE
);


CREATE TABLE route
(
    id             INTEGER AUTO_INCREMENT PRIMARY KEY,
    codeshare      VARCHAR(1),
    stops          INTEGER,
    equipment      VARCHAR(255),
    price          DOUBLE,
    src_airport_id INTEGER,
    dst_airport_id INTEGER,
    airline_id     INTEGER,
    INDEX          src_airport_index (src_airport_id),
    INDEX          dst_airport_index (dst_airport_id),
    INDEX          airline_index (airline_id),
    FOREIGN KEY (src_airport_id)
        REFERENCES airport (id)
        ON DELETE CASCADE,
    FOREIGN KEY (dst_airport_id)
        REFERENCES airport (id)
        ON DELETE CASCADE,
    FOREIGN KEY (airline_id)
        REFERENCES airline (id)
        ON DELETE CASCADE
);
