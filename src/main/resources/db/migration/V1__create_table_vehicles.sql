CREATE TABLE vehicles
(
    vehicle_id       UUID PRIMARY KEY,
    make             VARCHAR(100)             NOT NULL,
    model            VARCHAR(100)             NOT NULL,
    version          VARCHAR(100)             NOT NULL,
    year_fabrication VARCHAR(4)               NOT NULL,
    year_model       VARCHAR(4)               NOT NULL,
    kilometers       INT                      NOT NULL,
    color            VARCHAR(50)              NOT NULL,
    plate            VARCHAR(20)              NOT NULL,
    price            BIGINT                   NOT NULL,
    currency         VARCHAR(3)               NOT NULL,
    status           VARCHAR(50)              NOT NULL,
    created_at       TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at       TIMESTAMP WITH TIME ZONE
);
