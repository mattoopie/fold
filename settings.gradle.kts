rootProject.name = "fold"
include("lib")

plugins {
    id("com.gradle.develocity") version "4.2.1"
}

develocity {
    buildScan {
        publishing.onlyIf { System.getenv("CI") != null }
        termsOfUseUrl = "https://gradle.com/terms-of-service"
        termsOfUseAgree = "yes"
    }
}
