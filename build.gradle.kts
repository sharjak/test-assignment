plugins {
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management") apply false
    java
    id("jacoco")
}

group = "com.lhv.test"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "jacoco")

    dependencies {
        implementation("org.mapstruct:mapstruct:_")
        compileOnly("org.projectlombok:lombok:_")
        annotationProcessor("org.projectlombok:lombok:_")
        annotationProcessor("org.mapstruct:mapstruct-processor:_")
    }

    tasks.withType<Test>().configureEach {
        finalizedBy("jacocoTestReport")
    }

    tasks.named<JacocoReport>("jacocoTestReport") {
        dependsOn(tasks.test)

        reports {
            html.required.set(true)
            csv.required.set(false)
            xml.required.set(false)
        }
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-Amapstruct.defaultComponentModel=spring")
        options.generatedSourceOutputDirectory.set(file(layout.buildDirectory.dir("generated/sources/annotationProcessor/java/main")))
    }

    plugins.withId("org.springframework.boot") {
        tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
            enabled = (project.name == "app")
        }
        tasks.named<Jar>("jar") {
            enabled = (project.name != "app")
        }
    }
}

tasks.register<JacocoReport>("jacocoRootReport") {
    dependsOn(subprojects.map { it.tasks.named<JacocoReport>("jacocoTestReport") })

    executionData.setFrom(files(subprojects.map { it.layout.buildDirectory.file("jacoco/test.exec") }))

    additionalClassDirs.setFrom(
        files(subprojects.map { it.sourceSets["main"].output.classesDirs }).map { dir ->
            fileTree(dir) {
                exclude("**/*MapperImpl.class")
                exclude("**/LhvTestApplication.class")
            }
        }
    )

    additionalSourceDirs.setFrom(files(subprojects.map { it.sourceSets["main"].allSource.srcDirs }))

    reports {
        html.required.set(true)
        csv.required.set(false)
        xml.required.set(false)
    }
}

