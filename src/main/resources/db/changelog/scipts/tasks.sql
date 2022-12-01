-- liquibase formatted sql

-- changeset iavdeyev:1
CREATE TABLE owner (
                                   chat_id           BIGINT,
                                   name              TEXT,
                                   phone_number      TEXT,
                                   pet_id            BIGINT
);
