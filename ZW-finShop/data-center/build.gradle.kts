import com.google.protobuf.gradle.id

plugins {
    id("idea")
    id("org.springframework.boot")
    id("com.google.protobuf")
}

val errorProneAnnotations: String by project
val tomcatAnnotationsApi: String by project
val grpc: String = "1.63.0"
//val grpc: String by project
val grpcProtobuf: String by project


dependencies {
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("ch.qos.logback:logback-classic")
    implementation("org.flywaydb:flyway-core")
    implementation("io.r2dbc:r2dbc-postgresql")
    implementation("org.postgresql:postgresql")


    implementation("net.devh:grpc-server-spring-boot-starter:3.1.0.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

    implementation("com.google.code.findbugs:jsr305")
    implementation("com.google.code.gson:gson")
    implementation("com.google.protobuf:protobuf-java:${grpcProtobuf}")
    implementation("com.google.errorprone:error_prone_annotations:${errorProneAnnotations}")

    implementation("io.grpc:grpc-netty:${grpc}")
    implementation("io.grpc:grpc-protobuf:${grpc}")
    implementation("io.grpc:grpc-stub:${grpc}")
    implementation("io.grpc:grpc-services:${grpc}")

    runtimeOnly("org.flywaydb:flyway-database-postgresql")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

val protoSrcDir = "$projectDir/build/generated/sources/proto"

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