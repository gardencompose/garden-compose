plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    `maven-publish`
}

dependencies {
    api(libs.compose.runtime)
    api(libs.javagi.gtk)
    api(libs.javagi.adw)
    api(libs.kotlinx.datetime)
    api(project(":lib:core"))
    implementation(libs.slf4j.api)
    implementation(libs.kotlin.logging)

    detektPlugins(libs.detekt.compose)
    detektPlugins(libs.detekt.ktlint)

    testImplementation(kotlin("test"))
    testImplementation(libs.slf4j.simple)
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}

detekt {
    config.setFrom(rootProject.file("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
}

dokka {
    dokkaPublications.html {
        moduleName = "Gtk"
    }
}
