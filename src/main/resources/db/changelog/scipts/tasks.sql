-- liquibase formatted sql

-- changeset iavdeyev:1
CREATE TABLE owner (
                                   id                SERIAL,
                                   chat_id           TEXT,
                                   name              TEXT,
                                   phone_number      TEXT,
                                   pets            BIGINT
);
-- changeset dlukin:2
CREATE TABLE dogs (
    dog_id           SERIAL,
    name_dog         TEXT,
    owner_id       	BIGINT
);
