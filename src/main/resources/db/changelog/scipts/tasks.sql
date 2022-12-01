-- liquibase formatted sql

-- changeset iavdeyev:1
CREATE TABLE owner (
                                   id                SERIAL,
                                   chat_id           TEXT,
                                   name              TEXT,
                                   phone_number      TEXT,
                                   pet               BIGINT
);
-- changeset dlukin:2
CREATE TABLE pets (
    id           SERIAL,
    name_pet         TEXT,
    owner_id       	BIGINT,
    kind            TEXT
);
