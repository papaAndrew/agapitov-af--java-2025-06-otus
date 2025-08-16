plugins {
    id("com.github.johnrengelman.shadow")
}

group = "ru.sinara"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("ch.qos.logback:logback-classic")
}

tasks.test {
    useJUnitPlatform()
}