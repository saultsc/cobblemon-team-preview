plugins {
    id("com.gradleup.shadow")
    id("dev.architectury.loom")
    id("architectury-plugin")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    enableTransitiveAccessWideners.set(true)
    silentMojangMappingsLicense()

    @Suppress("UnstableApiUsage")
    mixin {
        useLegacyMixinAp.set(true)
        defaultRefmapName.set("mixins.${project.name}.refmap.json")
    }

    // Two named clients for testing battles locally against each other
    runs {
        named("client") {
            ideConfigGenerated(false)
        }
        create("client1") {
            client()
            configName = "Minecraft Client 1"
            programArgs("--username=AshKetchum", "--uuid=93e4e551-589a-41cb-ab2d-435266c8e035")
            ideConfigGenerated(true)
        }
        create("client2") {
            client()
            configName = "Minecraft Client 2"
            programArgs("--username=GaryOak")
            ideConfigGenerated(true)
        }
    }
}
val shadowCommon: Configuration = configurations.maybeCreate("shadowCommon")

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${property("fabric_loader_version")}")

    modRuntimeOnly("net.fabricmc.fabric-api:fabric-api:${property("fabric_api_version")}")
    modImplementation(fabricApi.module("fabric-networking-api-v1", property("fabric_api_version").toString()))

    //needed for cobblemon
    modImplementation("net.fabricmc:fabric-language-kotlin:${property("fabric_kotlin")}")
    modImplementation("com.cobblemon:fabric:${property("cobblemon_version")}") { isTransitive = false }
    // Showdown runtime: Cobblemon bundles these via jar-in-jar, which isn't loaded in dev
    runtimeOnly("org.graalvm.js:js:22.3.0")
    runtimeOnly("com.ibm.icu:icu4j:71.1")
    runtimeOnly("org.mongodb:mongodb-driver-sync:4.10.2")

    implementation(project(":common", configuration = "namedElements"))
    "developmentFabric"(project(":common", configuration = "namedElements"))
    shadowCommon(project(":common", configuration = "transformProductionFabric"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:${property("junit_version")}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${property("junit_version")}")
}

tasks {
    test {
        useJUnitPlatform()
    }

    processResources {
        inputs.property("version", project.version)
        inputs.property("mod_id", project.property("mod_id"))

        filesMatching("fabric.mod.json") {
            expand(project.properties)
        }
    }

    jar {
        archiveBaseName.set("${rootProject.property("archives_base_name")}-${project.name}")
        archiveClassifier.set("dev-slim")
    }

    shadowJar {
        archiveClassifier.set("dev-shadow")
        archiveBaseName.set("${rootProject.property("archives_base_name")}-${project.name}")
        configurations = listOf(shadowCommon)
    }

    remapJar {
        dependsOn(shadowJar)
        inputFile.set(shadowJar.flatMap { it.archiveFile })
        archiveBaseName.set("${rootProject.property("archives_base_name")}-${project.name}")
        archiveVersion.set("${rootProject.version}+${rootProject.property("minecraft_version")}")
        destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
    }
}
