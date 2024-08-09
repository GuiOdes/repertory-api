package com.guiodes.repertory.application.commons

import java.util.UUID

fun String.toUUID() = UUID.fromString(this)
