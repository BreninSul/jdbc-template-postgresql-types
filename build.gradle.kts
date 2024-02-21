import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.9.22"
    val springBootVersion = "3.2.2"
    id("java")
    id("net.thebugmc.gradle.sonatype-central-portal-publisher") version "1.1.1"
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version "1.1.4"
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.kapt") version kotlinVersion
}
val springBootVersion = "3.2.2"
val springTrxVersion = "6.1.3"
val kotlinVersion = "1.9.22"
val javaVersion = JavaVersion.VERSION_17

group = "io.github.breninsul"
version = "1.0.2"

java {
    sourceCompatibility = javaVersion
}

java {
    withJavadocJar()
    withSourcesJar()
}
repositories {
    mavenCentral()
}
tasks.compileJava {
    dependsOn.add(tasks.processResources)
}
tasks.compileKotlin {
    dependsOn.add(tasks.processResources)
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.postgresql:postgresql:42.7.1")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:$springBootVersion")
    api("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")
    kapt("org.springframework.boot:spring-boot-autoconfigure-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = javaVersion.majorVersion
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

signing {
    useGpgCmd()
}

centralPortal {
    pom {
        packaging = "jar"
        name.set("BreninSul Spring JDBC (Template/Client) PotgreSQL types")
        val repositoryName = "jdbc-template-postgresql-types"
        url.set("https://github.com/BreninSul/$repositoryName")
        description.set(
            """
            This is small lib for integration PostgreSQL types like jsonb and enum with JdbcClient/Template.
            It provides easy way to map  jsonb/json/enum in parameter
            Also it's possible to create own types avoiding PGObject getter/setter methods
            """.trimIndent(),
        )
        licenses {
            license {
                name.set("MIT License")
                url.set("http://opensource.org/licenses/MIT")
            }
        }
        scm {
            connection.set("scm:https://github.com/BreninSul/$repositoryName.git")
            developerConnection.set("scm:git@github.com:BreninSul/$repositoryName.git")
            url.set("https://github.com/BreninSul/$repositoryName")
        }
        developers {
            developer {
                id.set("BreninSul")
                name.set("BreninSul")
                email.set("brenimnsul@gmail.com")
                url.set("breninsul.github.io")
            }
        }
    }
}

tasks.jar {
    enabled = true
    archiveClassifier.set("")
}
