--liquibase formatted sql
--changeset hungdh:20220822
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE CUSTOMER
(
    CUSTOMER_ID     uuid PRIMARY KEY default uuid_generate_v4(),
    CUSTOMER_EMAIL  varchar(255),
    CUSTOMER_STATUS varchar(10),
    CREATED_AT      timestamp,
    UPDATED_AT      timestamp
);

CREATE TABLE ITEM
(
    ITEM_ID          uuid PRIMARY KEY default uuid_generate_v4(),
    ITEM_REF         uuid NOT NULL,
    ITEM_QUALITY     integer,
    ITEM_DESCRIPTION json NOT null,
    CREATED_AT       timestamp,
    UPDATED_AT       timestamp
);
CREATE TABLE CART
(
    CART_ID     uuid PRIMARY KEY default uuid_generate_v4(),
    CUSTOMER_ID uuid NOT NULL,
    CREATED_AT  timestamp,
    UPDATED_AT  timestamp,

    CONSTRAINT CUSTOMER_ID_REF FOREIGN KEY (CUSTOMER_ID)
        REFERENCES CUSTOMER (CUSTOMER_ID)
);
CREATE TABLE CART_ITEM
(
    CART_ITEM_ID uuid PRIMARY KEY default uuid_generate_v4(),
    CART_ID      uuid NOT NULL,
    ITEM_ID      uuid NOT NULL,
    CREATED_AT   timestamp,
    UPDATED_AT   timestamp,

    CONSTRAINT CART_ID_REF FOREIGN KEY (CART_ID)
        REFERENCES CART (CART_ID),

    CONSTRAINT ITEM_ID_REF FOREIGN KEY (ITEM_ID)
        REFERENCES ITEM (ITEM_ID)
);


CREATE TABLE ITEM_CREATED_EVENT
(
    ITEM_CREATED_EVENT_ID           uuid PRIMARY KEY default uuid_generate_v4(),

    ITEM_CREATED_EVENT_HASH_CONTENT varchar(255),
    ITEM_CREATED_EVENT_CONTENT      varchar(255),
    ITEM_ID                         uuid,        -- for partition & routing
    TRANSACTION_ID                  varchar(64), -- sha256
    SENT                            boolean,
    CREATED_AT                      timestamp
);

-- rollback drop table person;