plugins {
    id("java")
}

group = "dev.maksymzapisov.jsonplaceholder"
version = "1.0-SNAPSHOT"

val restAssuredVersion = "6.0.0"
val jacksonDatabindVersion = "2.21.1"
val assertJVersion = "3.27.7"
val junitVersion = "6.0.3"
val commonsValidatorVersion = "1.10.1"
val ownerVersion = "1.0.12"
val allureVersion = "2.34.0"
val commonsCodecVersion = "1.21.0"
val slf4jVersion = "2.0.17"
val rhinoVersion = "1.9.1"

repositories {
    mavenCentral()
}

// Override vulnerable transitive dependencies globally
configurations.all {
    resolutionStrategy {
        force("commons-codec:commons-codec:$commonsCodecVersion")
        force("com.fasterxml.jackson.core:jackson-databind:$jacksonDatabindVersion")
        force("org.mozilla:rhino:$rhinoVersion")
    }
}

dependencies {
    implementation("io.rest-assured:rest-assured:$restAssuredVersion")
    implementation("io.rest-assured:json-schema-validator:$restAssuredVersion")
    implementation("org.aeonbits.owner:owner:${ownerVersion}")
    testImplementation("commons-validator:commons-validator:$commonsValidatorVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("org.slf4j:slf4j-simple:${slf4jVersion}")
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-junit5")
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    reports {
        junitXml.required.set(true)
        html.required.set(true)
        junitXml.outputLocation.set(layout.projectDirectory.dir("test-results/junit/$name/xml"))
        html.outputLocation.set(layout.projectDirectory.dir("test-results/junit/$name/html"))
    }
}

tasks.register<Test>("contractTest") {
    group = "verification"
    description = "Runs tests tagged with Contract"
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
    useJUnitPlatform {
        includeTags("Contract")
    }
}

tasks.register<Test>("functionalTest") {
    group = "verification"
    description = "Runs tests tagged with Functional"
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
    useJUnitPlatform {
        includeTags("Functional")
    }
}

tasks.register<Test>("e2eTest") {
    group = "verification"
    description = "Runs tests tagged with E2E"
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
    useJUnitPlatform {
        includeTags("E2E")
    }
}