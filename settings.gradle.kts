rootProject.name = "agapitov-java"

pluginManagement {
    val jgitver: String by settings
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings
    val jib: String by settings
    val protobufVer: String by settings
    val sonarlint: String by settings
    val spotless: String by settings

    plugins {
        id("fr.brouillard.oss.gradle.jgitver") version jgitver
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("com.google.cloud.tools.jib") version jib
        id("com.google.protobuf") version protobufVer
        id("name.remal.sonarlint") version sonarlint
        id("com.diffplug.spotless") version spotless
    }
}

include("HW01-gradle")
include("HW04-Generics")
include("HW06-Annotations")
include("HW08-GC")
include("HW10-ByteCode")
include("HW12-Solid")
include("HW15-Patterns")
include("HW16-Serialization")
include("HW18-JDBC")
include("HW21-JPQL")
include("HW22-Cache")
include("HW24-WebServer")
include("HW25-DI")
include("HW28-springDataJdbc")
include("HW31-Executors")
include("HW32-concurrentCollections")
include("HW34-multiProcess")
include("HW38-webflux-chat")

include("ZW-finShop:bank-client")