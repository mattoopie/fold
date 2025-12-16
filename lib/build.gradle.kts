import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm

plugins {
    kotlin("jvm") version "2.2.21"
    `java-library`

    id("com.vanniktech.maven.publish") version "0.35.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:2.2.21"))
    implementation(kotlin("stdlib"))

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter:6.0.1")
    testImplementation("org.assertj:assertj-core:3.27.6")
}


group = "org.eend"
base {
    archivesName = "fold"
}

kotlin {
    jvmToolchain(21)
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

mavenPublishing {
    configure(
        KotlinJvm(
            javadocJar = JavadocJar.Javadoc(),
            sourcesJar = true,
        )
    )

    coordinates("org.eend", "fold")
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

    publishToMavenCentral(automaticRelease = true)

    signAllPublications()
}


tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(tasks.withType<Sign>())
}