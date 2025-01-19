plugins {
    kotlin("jvm") version "2.1.0"
}

group = "de.vulpescloud"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.vulpescloud.de/snapshots")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("de.vulpescloud:VulpesCloud-wrapper:1.0.0-alpha2")
    compileOnly("de.vulpescloud:VulpesCloud-bridge:1.0.0-alpha2")
    compileOnly("de.vulpescloud:VulpesCloud-api:1.0.0-alpha2")
    compileOnly("de.vulpescloud:VulpesCloud-node:1.0.0-alpha2")
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    compileOnly("org.json:json:20250107")
}

kotlin {
    jvmToolchain(21)
}