import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    alias(libs.plugins.shadow)
}

dependencies {
    "implementation"(project(":redis-configuration"))
    "implementation"(project(":redis-common-api"))
    "implementation"(project(":redis-common"))
}

tasks.withType<ShadowJar> {
    archiveBaseName.set(rootProject.name)
    archiveClassifier.set("")

    relocate("com.google.inject", "me.whereareiam.socialismus.library.guice")

    val defaultDestination = rootProject.layout.buildDirectory.dir("libs")

    val customOutputDir = if (project.hasProperty("output")) {
        project.layout.dir(project.provider { File(project.property("output").toString()) })
    } else {
        null
    }

    destinationDirectory.set(customOutputDir ?: defaultDestination)
}

tasks.named<Copy>("processResources") {
    filter<ReplaceTokens>(
        "tokens" to mapOf(
            "projectName" to rootProject.name,
            "projectVersion" to project.version
        )
    )
}

tasks.named<Jar>("jar") {
    dependsOn("shadowJar")
}