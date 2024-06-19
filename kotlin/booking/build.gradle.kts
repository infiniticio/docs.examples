
plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    // Apply the application plugin
    application
}

repositories {
    //mavenLocal()
    mavenCentral()
}

dependencies {
    // add a logger
    implementation("org.slf4j:slf4j-simple:2.0.3")
    // Infinitic version
    version = "0.13.3"
    // infinitic client
    implementation("io.infinitic:infinitic-client:$version")
    // infinitic worker
    implementation("io.infinitic:infinitic-worker:$version")
    // infinitic dashboard
    implementation("io.infinitic:infinitic-dashboard:$version")
    // Kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

}

application {
    mainClass.set("example.booking.WorkerKt")
}

task<JavaExec>("dispatch") {
    group = "infinitic"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("example.booking.ClientKt")
}

task<JavaExec>("dashboard") {
    group = "infinitic"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("example.booking.DashboardKt")
}
