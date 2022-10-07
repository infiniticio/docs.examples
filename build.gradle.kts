plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.6.10"

    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    mavenCentral()
    // needed for the dashboard
    maven("https://jitpack.io")
    mavenLocal()
}

dependencies {
    // add a logger
    implementation("org.slf4j:slf4j-simple:2.0.3")
    // infinitic client
    implementation("io.infinitic:infinitic-client:0.10.0")
    // infinitic worker
    implementation("io.infinitic:infinitic-worker:0.10.0")
    // infinitic dashboard
    implementation("io.infinitic:infinitic-dashboard:0.10.0")
}

application {
    // Define the main class for the application.
    mainClass.set("loyalty.AppKt")
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
