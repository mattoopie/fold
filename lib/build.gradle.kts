plugins {
    kotlin("jvm") version "2.0.0"
    `java-library`

    `maven-publish`
    signing
    base
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.assertj:assertj-core:3.25.3")
}


group = "org.eend"
base {
    archivesName = "fold"
}

kotlin {
    jvmToolchain(21)
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version
            )
        )
    }
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("fold") {
            artifactId = "fold"
            from(components["java"])

            pom {
                name = "Fold"
                description = "More advanced folds for Kotlin"
                url = "https://github.com/mattoopie/fold"
                licenses {
                    license {
                        name = "MIT License"
                        url = "http://www.opensource.org/licenses/mit-license.php"
                    }
                }
                developers {
                    developer {
                        id = "mattoopie"
                        name = "Marcel van Heerdt"
                        email = "developer@eend.org"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/mattoopie/fold.git"
                    developerConnection = "scm:git:ssh://github.com/mattoopie/fold.git"
                    url = "https://github.com/mattoopie/fold"
                }
            }
        }
    }

    repositories {
        maven {
            val isSnapshot = version.toString().endsWith("SNAPSHOT")
            if (isSnapshot) {
                name = "MavenCentralSnapshot"
                url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            } else {
                name = "MavenCentralStaging"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            }

            credentials {
                username = "${properties["sonatypeUsername"]}"
                password = "${properties["sonatypePassword"]}"
            }
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications.getByName("fold"))
}
