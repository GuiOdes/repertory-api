package com.guiodes.repertory.infra.database.repositories

import com.guiodes.repertory.application.gateways.BaseGateway
import com.guiodes.repertory.builders.BaseEntityBuilder
import com.guiodes.repertory.configs.IntegrationTest
import com.guiodes.repertory.domain.interfaces.BaseEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

abstract class BaseGatewayIT<E : BaseEntity>(
    private val repository: BaseGateway<E>,
    private val entityBuilder: BaseEntityBuilder<E>,
) : IntegrationTest() {
    @Test
    fun `Should test basic database operations`() {
        val entity = entityBuilder.build()

        repository.save(entity)

        val foundEntity = repository.findById(entity.id)

        val findAll = repository.findAll()

        assertThat(findAll).contains(entity)

        assertThat(foundEntity)
            .usingRecursiveAssertion()
            .isEqualTo(entity)

        repository.delete(entity)

        val deletedEntity = repository.findById(entity.id)

        assertThat(deletedEntity).isNull()
    }
}
