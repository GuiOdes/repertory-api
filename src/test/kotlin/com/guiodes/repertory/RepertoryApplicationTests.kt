package com.guiodes.repertory

import com.guiodes.repertory.configs.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RepertoryApplicationTests : IntegrationTest() {
    @Test
    fun contextLoads() {
        println("Hello World!")
    }
}
