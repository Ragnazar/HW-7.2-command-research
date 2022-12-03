-- liquibase formatted sql

-- changeset iavdeyev:1
CREATE TABLE owner (
                                   id                SERIAL,
                                   chat_id           TEXT,
                                   name              TEXT,
                                   phone_number      TEXT,
                                   pet_id            BIGINT
);
-- changeset iavdeyev:3
CREATE TABLE report (
                       id                SERIAL,
                       pet_id            BIGINT,
                       recording_date    TIMESTAMP,
                       photo             BYTEA,
                       diet              TEXT,
                       state_of_health   TEXT,
                       behavior_changes  TEXT
);