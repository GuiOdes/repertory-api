package com.guiodes.repertory.application.repositories

import java.util.UUID

interface BaseRepository<T> {
    fun save(entity: T): T

    fun update(entity: T): T

    fun delete(entity: T)

    fun findById(id: UUID): T?

    fun findAll(): List<T>
}
