plugins {
    kotlin("jvm") version "2.3.20"
    kotlin("plugin.serialization") version "2.3.20"
    id("com.gradleup.shadow") version "8.3.6"
    kotlin("kapt") version "2.3.20"
}

group = "org.vulpesstudios.vulpescloud"
version = "2.2.0"

repositories {
    mavenCentral()
    maven("https://repo.vulpesstudios.org/snapshots")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://buf.build/gen/maven")
}

dependencies {
    implementation("com.electronwill.night-config:json:3.6.0")
    compileOnly("org.vulpesstudios.vulpescloud:wrapper:3.0.0-beta7")
    compileOnly("org.vulpesstudios.vulpescloud:bridge:3.0.0-beta7")
    compileOnly("org.vulpesstudios.vulpescloud:api:3.0.0-beta7")
    compileOnly("org.vulpesstudios.vulpescloud:node:3.0.0-beta7")
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    kapt("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    compileOnly("org.json:json:20250517")
    compileOnly("org.incendo:cloud-core:2.0.0")
    compileOnly("org.incendo:cloud-annotations:2.0.0")
    compileOnly("org.incendo:cloud-kotlin-extensions:2.0.0")
    compileOnly("org.incendo:cloud-kotlin-coroutines:2.0.0")
    compileOnly("org.incendo:cloud-kotlin-coroutines-annotations:2.0.0")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
    compileOnly("build.buf.gen:vulpescloud_protospecs_grpc_kotlin:1.5.0.2.20260414175246.6a4bbab8e9d4")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(25)
}

tasks.shadowJar {
    archiveFileName.set("VulpesCloud-Proxy-Module.jar")
}