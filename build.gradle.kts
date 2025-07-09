plugins {
    kotlin("jvm") version "2.2.0"
    id("com.gradleup.shadow") version "8.3.8"
}

group = "de.vulpescloud"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.vulpescloud.de/snapshots")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("de.vulpescloud:VulpesCloud-wrapper:1.1.0")
    compileOnly("de.vulpescloud:VulpesCloud-bridge:1.0.0")
    compileOnly("de.vulpescloud:VulpesCloud-api:2.0.0")
    compileOnly("de.vulpescloud:VulpesCloud-node:1.0.0")
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    compileOnly("org.json:json:20250517")
    compileOnly("org.incendo:cloud-core:2.0.0")
    compileOnly("org.incendo:cloud-annotations:2.0.0")
    compileOnly("org.incendo:cloud-kotlin-extensions:2.0.0")
    compileOnly("org.incendo:cloud-kotlin-coroutines:2.0.0")
    compileOnly("org.incendo:cloud-kotlin-coroutines-annotations:2.0.0")
    compileOnly("redis.clients:jedis:6.0.0")
    // implementation("dev.jorel:commandapi-velocity-shade:9.6.2-SNAPSHOT")
}

kotlin {
    jvmToolchain(21)
}

tasks.shadowJar {
    archiveFileName.set("VulpesCloud-Proxy-Module.jar")
}