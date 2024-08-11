package com.guiodes.repertory.infra.database.expressions

object UserExpressions {
    const val SAVE = """
        INSERT INTO TB_USER (
            ID,
            NAME,
            EMAIL,
            PASSWORD,
            IS_ACTIVE,
            CREATED_AT,
            UPDATED_AT
        ) VALUES (
            :id,
            :name,
            :email,
            :password,
            :isActive,
            :createdAt,
            :updatedAt
        )
    """

    const val FIND = """
        SELECT
            ID,
            NAME,
            EMAIL,
            PASSWORD,
            IS_ACTIVE,
            CREATED_AT,
            UPDATED_AT
        FROM TB_USER
        WHERE IS_ACTIVE = TRUE
    """

    const val UPDATE = """
        UPDATE TB_USER SET
            NAME = :name,
            EMAIL = :email,
            PASSWORD = :password,
            IS_ACTIVE = :isActive,
            UPDATED_AT = :updatedAt
        WHERE ID = :id
    """

    const val SOFT_DELETE = """
        UPDATE TB_USER SET
            IS_ACTIVE = FALSE
        WHERE ID = :id
    """

    const val RESTORE = """
        UPDATE TB_USER SET
            IS_ACTIVE = TRUE
        WHERE ID = :id
    """

    const val ID = "ID = :id"

    const val EMAIL = "EMAIL = :email"
}
