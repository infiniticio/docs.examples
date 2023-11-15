plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    kotlin("jvm") version "1.9.10"

    // Apply the application plugin
    application
}

repositories {
    mavenCentral()
    mavenLocal()
    // necessary for the dashboard
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
    mainClass.set("hello.world.WorkerKt")
}

task("dispatch", JavaExec::class) {
    group = "infinitic"
    main = "hello.world.ClientKt"
    classpath = sourceSets["main"].runtimeClasspath
}

task("dashboard", JavaExec::class) {
    group = "infinitic"
    main = "hello.world.DashboardKt"
    classpath = sourceSets["main"].runtimeClasspath
    setArgsString("infinitic.yml")
}
