plugins {
    java
    id("jacoco")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:3.12.4")
    implementation("mysql:mysql-connector-java:8.0.21")
    implementation ("org.mindrot:jbcrypt:0.4")
    implementation ("com.h2database:h2:1.4.200")
    implementation ("org.springframework.security:spring-security-crypto:5.4.5")



}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "com.pongame.graphics.PongGame"
    }
    from(configurations.runtimeClasspath.get().filter { it.isDirectory }.map { it })
    from(configurations.runtimeClasspath.get().filter { it.isFile }.map { zipTree(it) })
}
