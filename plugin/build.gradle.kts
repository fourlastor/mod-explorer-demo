import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.AppendingTransformer

@Suppress(
    // known false positive: https://youtrack.jetbrains.com/issue/KTIJ-19369
    "DSL_SCOPE_VIOLATION"
)
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.shadow)
    `java-library`
}

group = "io.github.fourlastor"
version = "1.0-SNAPSHOT"

tasks.withType<Jar> {
    manifest {
        attributes["Plugin-Id"] = "welcome-plugin"
        attributes["Plugin-Version"] = "0.0.1"
        attributes["Plugin-Description"] = "My example plugin"
        attributes["Plugin-Provider"] = "fourlastor"
        attributes["Plugin-License"] = "Apache License 2.0"
    }
}

tasks.withType<ShadowJar> {
    transform(AppendingTransformer::class.java) {
        resource = "extensions.idx"
    }
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    implementation(libs.pf4j.core)
    kapt(libs.pf4j.core)
    implementation(project(":api"))
    api("com.squareup.okhttp3:okhttp:4.10.0")
}
