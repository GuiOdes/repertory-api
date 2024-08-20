package com.guiodes.repertory.infra.database.expressions

object InstrumentExpressions {
    const val CREATE = """
        INSERT INTO "INSTRUMENT" (
            ID,
            NAME,
            CREATED_AT,
            UPDATED_AT
        ) VALUES (
            :id,
            :name,
            :createdAt,
            :updatedAt
        )
    """

    const val FIND = """
        SELECT
            ID,
            NAME,
            CREATED_AT,
            UPDATED_AT
        FROM "INSTRUMENT"
    """

    const val EXISTS = """
        SELECT
            COUNT(1) > 0
        FROM "INSTRUMENT"
    """

    const val UPDATE = """
        UPDATE "INSTRUMENT" SET
            NAME = :name,
            UPDATED_AT = :updatedAt
        WHERE ID = :id
    """

    const val DELETE_BY_NAME = """
        DELETE FROM "INSTRUMENT"
        WHERE NAME = :name
    """

    const val ID = "ID = :id"

    const val NAME = "LOWER(NAME) LIKE LOWER(:name)"
}
