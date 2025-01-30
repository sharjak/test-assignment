plugins {
    id("org.liquibase.gradle") version "2.2.0"
    distribution
}
apply(plugin = "org.liquibase.gradle")

dependencies {
    liquibaseRuntime("org.liquibase:liquibase-core:4.22.0")
    liquibaseRuntime("com.h2database:h2")
    liquibaseRuntime("info.picocli:picocli:4.6.3")
}

liquibase {
    activities.register("main") {
        arguments = mapOf(
            "changelogFile" to "/changelog.yaml",
            "classpath" to "$projectDir/changelog",
            "url" to (project.findProperty("liquibaseDbUrl") ?: "jdbc:h2:file:$projectDir/LhvTestDatabase"),
            "username" to (project.findProperty("liquibaseUser") ?: "lhv_test_user"),
            "password" to (project.findProperty("liquibasePassword") ?: "lhv_test_password"),
            "logLevel" to "info",
            "liquibaseHubMode" to "off"
        )
    }
}

distributions {
    main {
        contents {
            from(".")
            include("changelog/**")
            into("/")
        }
    }
}
