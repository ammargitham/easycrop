plugins {
    kotlin("android")
    id("com.android.library")
    id("maven-publish")
    id("signing")
}

val composeBomVersion: String by project
val composeCompilerVersion: String by project

android {
    namespace = "com.mr0xf00.easycrop"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation("androidx.core:core-ktx:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
}

fun extProperty(key: String): String {
    return (extra.properties[key] ?: error("property $key not found in extras")) as String
}

publishing {
    val data = object {
        // val groupId = "io.github.mr0xf00"
        val groupId = "com.github.ammargitham"
        val artifactId = "easycrop"
        val version = "0.2.0"
        private val releasesRepoUrl =
            uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
        private val snapshotsRepoUrl =
            uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")

        val repoUrl = if (version.endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
    }

    publications {
        register<MavenPublication>("release") {
            groupId = data.groupId
            artifactId = data.artifactId
            version = data.version
            afterEvaluate {
                from(components["release"])
            }
            pom {
                name.set("EasyCrop")
                description.set("Image cropper for jetpack compose")
                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }
                url.set("https://github.com/mr0xf00/easycrop")
                scm {
                    connection.set("https://github.com/mr0xf00/easycrop.git")
                    url.set("https://github.com/mr0xf00/easycrop")
                }
                developers {
                    developer {
                        name.set("mr0xf00")
                        email.set("mr0xf00@proton.me")
                    }
                }
            }
        }
    }
    // publications {
    //     repositories {
    //         maven {
    //             name = "oss"
    //             url = data.repoUrl
    //             credentials {
    //                 username = extProperty("ossrh.user")
    //                 password = extProperty("ossrh.pass")
    //             }
    //         }
    //     }
    // }
}

// signing {
//     sign(publishing.publications)
// }
