CREATE TABLE "AUTHORITY"
(
    ID         UUID PRIMARY KEY NOT NULL,
    NAME       VARCHAR          NOT NULL,
    CREATED_AT TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE "AUTHORITY" IS 'Authority table';
COMMENT ON COLUMN "AUTHORITY".NAME IS 'Authority name';
COMMENT ON COLUMN "AUTHORITY".ID IS 'Authorities primary key';