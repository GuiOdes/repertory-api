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

    const val ID = "ID = :id"

    const val EMAIL = "EMAIL = :email"

    const val PASSWORD = "PASSWORD = :password"
}
