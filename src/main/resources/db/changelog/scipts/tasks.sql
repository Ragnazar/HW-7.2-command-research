-- liquibase formatted sql

-- changeset iavdeyev:1
CREATE TABLE owner (
                                   id                SERIAL,
                                   chat_id           TEXT,
                                   name              TEXT,
                                   phone_number      TEXT,
                                   pet_id            BIGINT
);
