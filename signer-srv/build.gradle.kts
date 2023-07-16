plugins {
    java
    id("application")
    id("org.springframework.boot") version "3.1.1"
    id("io.spring.dependency-management") version "1.1.0"

    id("myproject.java-conventions")
}

group = "vez"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    // Swagger UI (OpenAPI tools)
    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

    implementation ("org.springframework.boot:spring-boot-starter-web")
    compileOnly ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
}

application {
    mainClass.set("vez.signer.SignerApplication")
}
