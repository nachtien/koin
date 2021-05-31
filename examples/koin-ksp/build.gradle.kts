
plugins {
    id("com.google.devtools.ksp") version "1.5.10-1.0.0-beta01"
    kotlin("jvm")
}
//buildscript {
//    dependencies {
//        classpath(kotlin("gradle-plugin", version = "1.5.10"))
//    }
//}

repositories {
    mavenCentral()
    google()
}

dependencies {
    val kspVersion = "1.5.10-1.0.0-beta01"
    val kotlinVersion = "1.5.10"

//    implementation("com.google.auto.service:auto-service-annotations:1.0")
//    ksp("dev.zacsweers.autoservice:auto-service-ksp:0.4.0")

    implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
    implementation("com.google.devtools.ksp:symbol-processing:$kspVersion")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlinVersion")
    implementation(project(":koin-annotations"))
}