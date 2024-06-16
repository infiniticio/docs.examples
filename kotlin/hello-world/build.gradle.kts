plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    kotlin("jvm") version "1.9.20"

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

    // infinitic version
    version = "0.12.0"
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

task<JavaExec>("dashboard") {
    group = "infinitic"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("hello.world.DashboardKt")

}

task<JavaExec>("dispatch") {
    group = "infinitic"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("hello.world.ClientKt")
}
