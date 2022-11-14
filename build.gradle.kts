import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.7.10"
}

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "17" }

group = "org.demo"
version = "1.0-SNAPSHOT"

repositories {
  maven(url = "https://artifactory.sensefly.io/artifactory/repos") {
    credentials {
      username = project.properties["artifactory_user"] as String?
      password = project.properties["artifactory_password"] as String?
    }
  }
}

val geotoolsVersion by extra { "27.2" }
val junitVersion by extra { "5.9.0" }

dependencies {
  implementation("org.geotools", "gt-main", geotoolsVersion)
  implementation("org.geotools.xsd", "gt-xsd-kml", geotoolsVersion)

  testImplementation(kotlin("test-junit5"))
  testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junitVersion)
}

tasks {
  test {
    useJUnitPlatform()
  }
}
