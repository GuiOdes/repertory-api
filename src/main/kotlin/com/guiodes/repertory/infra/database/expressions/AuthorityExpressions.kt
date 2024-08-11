package com.guiodes.repertory.infra.database.expressions

object AuthorityExpressions {
    const val CREATE = """
        INSERT INTO "AUTHORITY" (
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
        FROM "AUTHORITY"
    """

    const val UPDATE = """
        UPDATE "AUTHORITY" SET
            NAME = :name,
            UPDATED_AT = :updatedAt
        WHERE ID = :id
    """

    const val DELETE = """
        DELETE FROM "AUTHORITY"
        WHERE ID = :id
    """

    const val ID = "ID = :id"
}
