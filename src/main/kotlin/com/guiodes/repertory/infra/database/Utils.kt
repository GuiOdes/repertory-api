package com.guiodes.repertory.infra.database

import kotlin.math.max

fun String.addCondition(sql: String): String = "$this ${if (containsWhereAfterFrom(this)) "AND" else "WHERE"} $sql"

private fun containsWhereAfterFrom(string: String) = string.substring(max(string.lowercase().lastIndexOf("from"), 0)).contains("WHERE")
