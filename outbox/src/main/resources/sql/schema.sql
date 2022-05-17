CREATE TABLE IF NOT EXISTS items (
    id           uuid UNIQUE NOT NULL,
    type         varchar NOT NULL,
    properties   varchar NOT NULL,
    created      timestamp NOT NULL,
    CONSTRAINT items_pk PRIMARY KEY (id)
);

CREATE INDEX items_type ON items (type);