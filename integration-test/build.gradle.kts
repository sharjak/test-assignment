dependencies {
    implementation(project(":app"))
    implementation(project(":domain"))
    implementation(project(":adapters:web"))

    implementation("org.springframework.boot:spring-boot-starter-web:_")

    testImplementation("org.springframework.boot:spring-boot-starter-test:_")

    testImplementation("org.junit.jupiter:junit-jupiter-api:_")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:_")

    testImplementation("org.mockito:mockito-core:_")
    testImplementation("org.mockito:mockito-junit-jupiter:_")

    runtimeOnly("com.h2database:h2:_")
}

tasks.test {
    useJUnitPlatform()
}
