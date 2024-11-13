rootProject.name = "fold"
include("lib")

plugins {
    id("com.gradle.develocity") version "3.18.2"
}

develocity {
    buildScan {
        publishing.onlyIf { System.getenv("CI") != null }
        termsOfUseUrl = "https://gradle.com/terms-of-service"
        termsOfUseAgree = "yes"
    }
}
