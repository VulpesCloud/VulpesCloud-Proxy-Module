plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"
    id("com.gradleup.shadow") version "8.3.6"
}

group = "de.vulpescloud"
version = "1.1.0"

repositories {
    mavenCentral()
    maven("https://repo.vulpescloud.de/snapshots")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://buf.build/gen/maven")
}

dependencies {
    implementation("com.electronwill.night-config:json:3.8.3")
    compileOnly("de.vulpescloud:wrapper:3.0.0")
    compileOnly("de.vulpescloud:bridge:3.0.0")
    compileOnly("de.vulpescloud:api:3.0.0")
    compileOnly("de.vulpescloud:node:3.0.0")
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    compileOnly("org.json:json:20250517")
    compileOnly("org.incendo:cloud-core:2.0.0")
    compileOnly("org.incendo:cloud-annotations:2.0.0")
    compileOnly("org.incendo:cloud-kotlin-extensions:2.0.0")
    compileOnly("org.incendo:cloud-kotlin-coroutines:2.0.0")
    compileOnly("org.incendo:cloud-kotlin-coroutines-annotations:2.0.0")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    compileOnly("build.buf.gen:vulpescloud_protospecs_grpc_kotlin:1.5.0.1.20250930184830.3aeb7ae6f9e8")
}

kotlin {
    jvmToolchain(21)
}

tasks.shadowJar {
    archiveFileName.set("Proxy-Module.jar")
}