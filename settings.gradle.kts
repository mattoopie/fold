rootProject.name = "fold"
include("lib")

plugins {
    id("com.gradle.enterprise") version "3.17.4"
}

gradleEnterprise {
    if (System.getenv("CI") != null) {
        buildScan {
            publishAlways()
            termsOfServiceUrl = "https://gradle.com/terms-of-service"
            termsOfServiceAgree = "yes"
        }
    }
}
