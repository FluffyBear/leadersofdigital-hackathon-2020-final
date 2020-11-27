import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    idea
    war
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.noarg") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
    kotlin("plugin.serialization") version "1.3.72"
    jacoco
}

group = "syktykpyk"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    mavenLocal()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
    implementation("io.github.microutils:kotlin-logging:1.7.9")
    implementation("com.google.code.gson:gson:2.8.5")
    runtime("org.springframework.boot:spring-boot-starter-tomcat")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
}

apply {
    plugin("kotlin-jpa")
}

tasks.withType<Test> {
    useJUnitPlatform()
    maxHeapSize = "8000m"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}