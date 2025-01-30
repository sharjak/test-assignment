dependencies {
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:_")

    runtimeOnly("com.h2database:h2:_")
}