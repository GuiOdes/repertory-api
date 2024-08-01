package com.guiodes.repertory.infra.database

fun String.addWhere(vararg conditions: String): String = this + " WHERE " + conditions.joinToString(" AND ")
