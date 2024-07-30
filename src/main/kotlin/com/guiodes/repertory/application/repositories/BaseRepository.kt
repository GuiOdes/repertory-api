package com.guiodes.repertory.application.repositories

interface BaseRepositoryT<T> {
    fun save(entity: T): T
    fun update(entity: T): T
    fun delete(entity: T)
    fun findById(id: Long): T?
    fun findAll(): List<T>
}