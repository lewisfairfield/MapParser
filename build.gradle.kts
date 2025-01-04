plugins {
    id("java")
    id("com.mineplex.sdk.plugin") version "1.11.0"
    id("maven-publish")
}

group = "net.plexverse"
version = "1.1"

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.opencollab.dev/main/")
    }
    maven {
        url = uri("https://s01.oss.sonatype.org/content/groups/public/")
    }
    maven {
        url = uri("https://repo.codemc.io/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.dmulloy2.net/repository/public/")
    }
    maven {
        url = uri("https://repo.md-5.net/content/groups/public/")
    }
    maven {
        url = uri("https://mvn.lumine.io/repository/maven-public/")
    }
    maven {
        url = uri("https://jitpack.io")
    }
    maven {
        url = uri("https://repo.xenondevs.xyz/releases")
    }
}

dependencies {
    implementation("xyz.xenondevs.invui:invui:1.33")
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot:1.19.2-R0.1-SNAPSHOT")
    implementation("com.google.code.gson:gson:2.10.1")
    compileOnly("org.projectlombok:lombok:1.18.30")
    implementation("com.oop:memory-store:4.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.register<Jar>("shadowJar") {
    archiveBaseName.set("MapParser")
    archiveVersion.set("1.0-SNAPSHOT")
}

tasks.build {
    dependsOn("shadowJar")
}
