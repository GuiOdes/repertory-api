import kotlinx.kover.gradle.plugin.dsl.CoverageUnit

plugins {
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    id("org.jetbrains.kotlinx.kover") version "0.8.3"
}

group = "com.guiodes"
version = "0.0.1-SNAPSHOT"

val postgresSqlTestContainersVersion = "1.20.0"
val mockkVersion = "1.13.3"
val springMockkVersion = "4.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.flywaydb:flyway-core")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:10.16.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:3.3.2")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("com.ninja-squad:springmockk:$springMockkVersion")
    testImplementation("org.springframework.boot:spring-boot-testcontainers:3.3.2")
    testImplementation("org.testcontainers:junit-jupiter:1.20.0")
    testImplementation("org.testcontainers:postgresql:$postgresSqlTestContainersVersion")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named("koverHtmlReport") {
    dependsOn("test")
}

tasks.named("build") {
    dependsOn("koverHtmlReport")
}

val koverClassesToExclude =
    listOf(
        "com.guiodes.repertory.application.usecases.BuildJwtTokenUseCase",
        "com.guiodes.repertory.RepertoryApplicationKt",
    )

val koverPackagesToExclude = emptyList<String>()

kover {
    reports {
        filters {
            excludes {
                classes(koverClassesToExclude)
                packages(koverPackagesToExclude)
            }
        }
        verify {
            rule("branch-coverage") {
                bound {
                    coverageUnits = CoverageUnit.BRANCH
                    minValue = 90
                }
            }

            rule("line-coverage") {
                bound {
                    coverageUnits = CoverageUnit.LINE
                    minValue = 90
                }
            }
        }
    }
}
