import com.google.protobuf.gradle.id

val errorProneAnnotations: String by project
val tomcatAnnotationsApi: String by project
val grpc: String by project
val grpcProtobuf: String by project
val protoSrcDir = "$projectDir/build/generated/sources/proto"

plugins {
    id("idea")
    id("com.google.protobuf")
    id("com.google.cloud.tools.jib") version "3.5.2"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.kafka:spring-kafka")

    implementation("com.google.code.findbugs:jsr305")
    implementation("com.google.protobuf:protobuf-java:${grpcProtobuf}")
    implementation("com.google.errorprone:error_prone_annotations:${errorProneAnnotations}")
    implementation("io.grpc:grpc-netty:${grpc}")
    implementation("io.grpc:grpc-protobuf:${grpc}")
    implementation("io.grpc:grpc-stub:${grpc}")

    implementation("org.webjars:webjars-locator-core")
    implementation("org.webjars:sockjs-client")
    implementation("org.webjars:stomp-websocket")
    implementation("org.webjars:bootstrap")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

jib {
    container {
        creationTime.set("USE_CURRENT_TIMESTAMP")
    }
    from {
        image = "bellsoft/liberica-openjdk-alpine-musl:21.0.1"
    }

    to {
        image = "papaandrew/finshop-bank-client"
        tags = setOf(project.version.toString())
        auth {
            username = System.getenv("GITHUB_USERNAME")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}
idea {
    module {
        sourceDirs = sourceDirs.plus(file("$protoSrcDir/main/java"))
        sourceDirs = sourceDirs.plus(file("$protoSrcDir/main/grpc"))
    }
}

sourceSets {
    main {
        java {
            srcDirs(
                "$protoSrcDir/main/java",
                "$protoSrcDir/main/grpc"
            )
        }
    }
}


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$grpcProtobuf"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpc"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc") {
                    outputSubDir = "grpc"
                }
            }
        }
    }
}

tasks.named("generateProto") {
    dependsOn(tasks.named("processResources"))
}

tasks.named("clean") {
    doLast {
        delete(protoSrcDir)
    }
}