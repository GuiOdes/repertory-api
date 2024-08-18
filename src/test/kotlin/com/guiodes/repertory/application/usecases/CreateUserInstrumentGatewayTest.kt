package com.guiodes.repertory.application.usecases

import com.guiodes.repertory.application.exceptions.NotFoundException
import com.guiodes.repertory.application.gateways.InstrumentGateway
import com.guiodes.repertory.application.gateways.UserGateway
import com.guiodes.repertory.application.gateways.UserInstrumentGateway
import com.guiodes.repertory.builders.InstrumentBuilder
import com.guiodes.repertory.builders.UserBuilder
import com.guiodes.repertory.configs.UnitTest
import com.guiodes.repertory.domain.models.UserInstrument
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateUserInstrumentGatewayTest(
    @MockK private val gateway: UserInstrumentGateway,
    @MockK private val userGateway: UserGateway,
    @MockK private val instrumentGateway: InstrumentGateway,
): UnitTest() {

    @InjectMockKs
    private lateinit var createUserInstrumentGateway: CreateUserInstrumentGateway

    @Test
    fun `should create user instrument`() {
        val user = UserBuilder().build()
        val instrument = InstrumentBuilder().build()
        val userInstrument = UserInstrument(userId = user.id, instrumentId = instrument.id)

        every { userGateway.existsById(user.id) } returns true
        every { instrumentGateway.existsById(instrument.id) } returns true
        every { gateway.save(userInstrument) } returns userInstrument

        createUserInstrumentGateway.execute(user.id, instrument.id)

        verify(exactly = 1) { gateway.save(userInstrument) }
        verify(exactly = 1) { userGateway.existsById(user.id) }
        verify(exactly = 1) { instrumentGateway.existsById(instrument.id) }
    }

    @Test
    fun `should throw exception when user not found`() {
        val user = UserBuilder().build()
        val instrument = InstrumentBuilder().build()

        every { userGateway.existsById(user.id) } returns false

        assertThrows<NotFoundException> {
            createUserInstrumentGateway.execute(user.id, instrument.id)
        }

        verify(exactly = 1) { userGateway.existsById(user.id) }
        verify(exactly = 0) { instrumentGateway.existsById(instrument.id) }
        verify(exactly = 0) { gateway.save(any()) }
    }

    @Test
    fun `should throw exception when instrument not found`() {
        val user = UserBuilder().build()
        val instrument = InstrumentBuilder().build()

        every { userGateway.existsById(user.id) } returns true
        every { instrumentGateway.existsById(instrument.id) } returns false

        assertThrows<NotFoundException> {
            createUserInstrumentGateway.execute(user.id, instrument.id)
        }

        verify(exactly = 1) { userGateway.existsById(user.id) }
        verify(exactly = 1) { instrumentGateway.existsById(instrument.id) }
        verify(exactly = 0) { gateway.save(any()) }
    }
}