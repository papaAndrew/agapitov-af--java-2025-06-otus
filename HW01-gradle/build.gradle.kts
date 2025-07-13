import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id ("com.github.johnrengelman.shadow")
}

dependencies {
    implementation("ch.qos.logback:logback-classic")
    // https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("com.google.guava:guava")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("gradleHelloOtus")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.sinara.HelloOtus"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}

