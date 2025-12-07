import com.google.protobuf.gradle.id

val errorProneAnnotations: String by project
val tomcatAnnotationsApi: String by project
val grpc: String by project
val grpcProtobuf: String by project
val protoSrcDir = "$projectDir/build/generated/sources/proto"

plugins {
    id("idea")
    id("com.google.protobuf")

}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

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