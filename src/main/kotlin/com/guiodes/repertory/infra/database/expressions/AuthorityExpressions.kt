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

    const val SET_TO_USER = """
        INSERT INTO "USER_AUTHORITY" (
            USER_ID,
            AUTHORITY_ID
        ) VALUES (
            :userId,
            :authorityId
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

    const val FIND_USER_AUTHORITY = """
        SELECT
            A.ID,
            A.NAME,
            A.CREATED_AT,
            A.UPDATED_AT
        FROM "AUTHORITY" A
        JOIN "USER_AUTHORITY" UA ON A.ID = UA.AUTHORITY_ID
        WHERE UA.USER_ID = :userId
    """

    const val UPDATE = """
        UPDATE "AUTHORITY" SET
            NAME = :name,
            UPDATED_AT = :updatedAt
        WHERE ID = :id
    """

    const val DELETE_BY_NAME = """
        DELETE FROM "AUTHORITY"
        WHERE NAME = :name
    """

    const val ID = "ID = :id"
}
