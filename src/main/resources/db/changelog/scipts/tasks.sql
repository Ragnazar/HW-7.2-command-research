-- liquibase formatted sql

-- changeset iavdeyev:1

CREATE TABLE Owner
(
    chatId        TEXT NOT NULL,
    name          TEXT NOT NULL,
    phone_number  TEXT,
    shelterButton TEXT,
    volunteerChat BOOLEAN NOT NULL,
    pet_id        BIGINT,
    CONSTRAINT pk_owner PRIMARY KEY (chatId)
);

-- changeset dlukin:2
CREATE TABLE pets (
    id           SERIAL,
    name_pet         TEXT,
    owner_id       	BIGINT,
    kind            TEXT,
    report_id       BIGINT
);

-- changeset iavdeyev:3
CREATE TABLE report (
                       id                SERIAL,
                       recording_date    TIMESTAMP,
                       photo             BYTEA,
                       diet              TEXT,
                       state_of_health   TEXT,
                       behavior_changes  TEXT
                       
);
-- changeset NataliShilova:4
CREATE TABLE Notification
(
    id        BIGINT  NOT NULL,
    text      VARCHAR(255)NOT NULL,
    messageId INTEGER NOT NULL,
    CONSTRAINT pk_notification PRIMARY KEY (id)
);