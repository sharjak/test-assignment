pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        id("de.fayard.refreshVersions") version "0.60.5"
    }
}

plugins {
    id("de.fayard.refreshVersions")
}


rootProject.name = "lhv-test"
include("app", "adapters:web", "domain", "liquibase", "adapters:jdbc", "integration-test")
