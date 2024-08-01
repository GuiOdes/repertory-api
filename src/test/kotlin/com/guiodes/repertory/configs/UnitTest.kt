package com.guiodes.repertory.configs

import io.mockk.clearAllMocks
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UnitTest {
    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }
}
