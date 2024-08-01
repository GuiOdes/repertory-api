package com.guiodes.repertory.configs

import org.springframework.context.ConfigurableApplicationContext

interface IntegrationTestsConfiguration {
    fun configure(applicationContext: ConfigurableApplicationContext)
}
