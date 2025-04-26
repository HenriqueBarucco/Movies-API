plugins {
    kotlin("jvm")
}

group = "com.henriquebarucco"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
