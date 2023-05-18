plugins {
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
}

nexusPublishing {
    this.repositories {
        sonatype {
            stagingProfileId.set("${properties["sonatypeProfileId"]}")
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}
