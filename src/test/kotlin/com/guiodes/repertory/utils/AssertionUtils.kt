package com.guiodes.repertory.utils

import org.assertj.core.api.ObjectAssert
import java.time.LocalDateTime

fun <ACTUAL> ObjectAssert<ACTUAL>.isEqualToIgnoringDates(entity: ACTUAL) =
    this
        .usingRecursiveComparison()
        .ignoringFieldsOfTypes(LocalDateTime::class.java)
        .isEqualTo(entity)
