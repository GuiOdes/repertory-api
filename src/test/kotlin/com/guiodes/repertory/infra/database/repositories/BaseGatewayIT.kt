package com.guiodes.repertory.infra.database.repositories

import com.guiodes.repertory.builders.BaseEntityBuilder
import com.guiodes.repertory.configs.IntegrationTest
import com.guiodes.repertory.domain.interfaces.BaseEntity
import com.guiodes.repertory.utils.isEqualToIgnoringDates
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

abstract class BaseRepositoryIT<E: BaseEntity>(
    private val repository: BaseRepository<E>,
    private val entityBuilder: BaseEntityBuilder<E>
): IntegrationTest() {

    @Test
    fun `Should test basic database operations`() {
        val entity = entityBuilder.build()

        repository.save(entity)

        val foundEntity = repository.findById(entity.id)

        val findAll = repository.findAll()

        assertThat(findAll.size).isEqualTo(1)
        assertThat(findAll.first()).isEqualToIgnoringDates(entity)

        assertThat(foundEntity).isEqualToIgnoringDates(entity)

        repository.delete(entity)

        val deletedEntity = repository.findById(entity.id)

        assertThat(deletedEntity).isNull()
    }
}
