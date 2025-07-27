plugins {
    id("java")
}

group = "ru.sinara"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation ("org.assertj:assertj-core")
}

tasks.test {
    useJUnitPlatform()
}