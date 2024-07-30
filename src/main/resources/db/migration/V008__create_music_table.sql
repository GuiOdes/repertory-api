CREATE TABLE "MUSIC"
(
    ID                  UUID PRIMARY KEY                        NOT NULL,
    NAME                VARCHAR                                 NOT NULL,
    "KEY"               VARCHAR                                 NOT NULL,
    CHORD_LINK          VARCHAR                                 NOT NULL,
    LYRICS_LINK         VARCHAR                                 NOT NULL,
    VIDEO_LINK          VARCHAR                                 NOT NULL,
    CAPO                INTEGER                                 NOT NULL,
    IS_READY_TO_PLAY    BOOLEAN                                 NOT NULL DEFAULT FALSE,
    WORSHIP_MINISTRY_ID UUID REFERENCES "WORSHIP_MINISTRY" (ID) NOT NULL,
    CREATED_AT          TIMESTAMP                               NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT          TIMESTAMP                               NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE "MUSIC" IS 'Music table';
COMMENT ON COLUMN "MUSIC".NAME IS 'Music name';
COMMENT ON COLUMN "MUSIC"."KEY" IS 'Music key';
COMMENT ON COLUMN "MUSIC".CHORD_LINK IS 'Music chord link';
COMMENT ON COLUMN "MUSIC".LYRICS_LINK IS 'Music lyrics link';
COMMENT ON COLUMN "MUSIC".VIDEO_LINK IS 'Music video link';
COMMENT ON COLUMN "MUSIC".CAPO IS 'Music capo';
COMMENT ON COLUMN "MUSIC".IS_READY_TO_PLAY IS 'Music ready to play flag';
COMMENT ON COLUMN "MUSIC".WORSHIP_MINISTRY_ID IS 'Worship ministry id';
COMMENT ON COLUMN "MUSIC".ID IS 'Music primary key';