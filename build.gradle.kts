plugins {
    kotlin("jvm") version "2.1.10"
    id("com.gradleup.shadow") version "8.3.6"
}

group = "de.vulpescloud"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.vulpescloud.de/snapshots")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("de.vulpescloud:VulpesCloud-wrapper:2.0.0-ALPHA")
    compileOnly("de.vulpescloud:VulpesCloud-bridge:2.0.0-ALPHA")
    compileOnly("de.vulpescloud:VulpesCloud-api:2.0.0-ALPHA")
    compileOnly("de.vulpescloud:VulpesCloud-node:2.0.0-ALPHA")
    compileOnly("de.vulpescloud:JedisWrapper:1.1.0")
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    compileOnly("org.json:json:20250107")
    compileOnly("org.incendo:cloud-core:2.0.0")
    compileOnly("org.incendo:cloud-annotations:2.0.0")
    compileOnly("org.incendo:cloud-kotlin-extensions:2.0.0")
    compileOnly("org.incendo:cloud-kotlin-coroutines:2.0.0")
    compileOnly("org.incendo:cloud-kotlin-coroutines-annotations:2.0.0")
    implementation("io.insert-koin:koin-core:4.0.3")
    implementation("com.electronwill.night-config:json:3.6.0")
}

kotlin {
    jvmToolchain(21)
}

tasks.shadowJar {
    archiveFileName.set("VulpesCloud-Proxy-Module.jar")
}