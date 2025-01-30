plugins {
    id("org.liquibase.gradle")
}

dependencies {
    implementation("org.liquibase:liquibase-core:_")
    runtimeOnly("com.h2database:h2:_")

    liquibaseRuntime("org.liquibase:liquibase-core:_")
    liquibaseRuntime("info.picocli:picocli:_")
    liquibaseRuntime("com.h2database:h2:_")
}

liquibase {
    activities.register("main") {
        arguments = mapOf(
            "changelogFile" to "/changelog/changelog.yaml",
            "classpath" to "$projectDir/changelog",
            "url" to (project.findProperty("liquibaseDbUrl") ?: "jdbc:h2:file:$projectDir/LhvTestDatabase"),
            "username" to (project.findProperty("liquibaseUser") ?: "lhv_test_user"),
            "password" to (project.findProperty("liquibasePassword") ?: "lhv_test_password"),
            "logLevel" to "info"
        )
    }
}
