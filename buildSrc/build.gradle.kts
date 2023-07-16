plugins {
    id("groovy-gradle-plugin")
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
}

dependencies {
    implementation ("com.github.spotbugs.snom:spotbugs-gradle-plugin:5.0.12")
}

tasks {
    test {
        useJUnitPlatform()
        testLogging.showExceptions = true
    }
}