package com.guiodes.repertory.application.exceptions

import com.guiodes.repertory.domain.interfaces.BaseEntity
import kotlin.reflect.KClass

class NotFoundException(
    entity: KClass<out BaseEntity>,
) : RuntimeException("${entity.simpleName} not found")
