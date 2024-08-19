package com.guiodes.repertory.application.gateways

import java.util.UUID

interface BaseGateway<T> {
    fun save(entity: T): T

    fun update(entity: T): T

    fun findById(id: UUID): T?
}
