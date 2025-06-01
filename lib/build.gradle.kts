import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("jvm") version "2.1.21"
    `java-library`

    id("com.vanniktech.maven.publish") version "0.32.0"
    base
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:2.1.21"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.0")
    testImplementation("org.assertj:assertj-core:3.27.3")
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
}


mavenPublishing {
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

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)

    signAllPublications()
}

tasks.withType<PublishToMavenRepository>() {
    dependsOn(tasks.withType<Sign>())
}