-- liquibase formatted sql

-- changeset iavdeyev:1

CREATE TABLE Owner
(
    chatId        VARCHAR(255) NOT NULL,
    name          VARCHAR(255) NOT NULL,
    phone_number  VARCHAR(255),
    shelterButton VARCHAR(255),
    volunteerChat BOOLEAN      NOT NULL,
    pet_id        BIGINT,
    CONSTRAINT pk_owner PRIMARY KEY (chatId)
);

-- changeset NataliShilova:1
CREATE TABLE Notification
(
    id        BIGINT  NOT NULL,
    text      VARCHAR(255)NOT NULL,
    messageId INTEGER NOT NULL,
    CONSTRAINT pk_notification PRIMARY KEY (id)
);