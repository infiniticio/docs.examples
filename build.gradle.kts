plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.6.10"

    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    mavenCentral()
    mavenLocal()
    // needed for the dashboard
    maven("https://jitpack.io")
}

dependencies {
    // add a logger
    implementation("org.slf4j:slf4j-simple:2.0.3")
    // infinitic version
    version = "0.11.0"
    // infinitic client
    implementation("io.infinitic:infinitic-client:$version")
    // infinitic worker
    implementation("io.infinitic:infinitic-worker:$version")
    // infinitic dashboard
    implementation("io.infinitic:infinitic-dashboard:$version")
}

application {
    // Define the main class for the application.
    mainClass.set("loyalty.WorkerKt")
}

task("dispatch", JavaExec::class) {
    group = "infinitic"
    main = "loyalty.ClientKt"
    classpath = sourceSets["main"].runtimeClasspath
}

task("dashboard", JavaExec::class) {
    group = "infinitic"
    main = "loyalty.DashboardKt"
    classpath = sourceSets["main"].runtimeClasspath
}
