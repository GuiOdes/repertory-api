package com.guiodes.repertory.infra.database.repositories

import com.guiodes.repertory.application.gateways.BaseGateway
import com.guiodes.repertory.builders.BaseEntityBuilder
import com.guiodes.repertory.configs.IntegrationTest
import com.guiodes.repertory.domain.interfaces.BaseEntity
import com.guiodes.repertory.utils.isEqualToIgnoringDates
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

abstract class BaseGatewayIT<E : BaseEntity>(
    private val gateway: BaseGateway<E>,
    private val entityBuilder: BaseEntityBuilder<E>,
) : IntegrationTest() {
    @Test
    fun `Should test basic database operations`() {
        val entity = entityBuilder.build()

        gateway.save(entity)

        val foundEntity = gateway.findById(entity.id)

        assertThat(foundEntity).isEqualToIgnoringDates(entity)

        gateway.delete(entity)

        val deletedEntity = gateway.findById(entity.id)

        assertThat(deletedEntity).isNull()
    }
}
