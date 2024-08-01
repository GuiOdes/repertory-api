package com.guiodes.repertory.infra.database

fun String.addWhere(vararg conditions: String): String {
    return this + " WHERE " + conditions.joinToString(" AND ")
}