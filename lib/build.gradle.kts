import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    `java-library`

    `maven-publish`
    signing
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("org.assertj:assertj-core:3.25.1")
}


group = "org.eend"
archivesName.set("fold")

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
                name.set("Fold")
                description.set("More advanced folds for Kotlin")
                url.set("https://github.com/mattoopie/fold")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        id.set("mattoopie")
                        name.set("Marcel van Heerdt")
                        email.set("developer@eend.org")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/mattoopie/fold.git")
                    developerConnection.set("scm:git:ssh://github.com/mattoopie/fold.git")
                    url.set("https://github.com/mattoopie/fold")
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
