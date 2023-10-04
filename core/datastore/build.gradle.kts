plugins {
    id("safevault.android.library")
    id("safevault.android.hilt")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.mitch.safevault.core.datastore"
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(libs.datastore.proto)
    implementation(libs.protobuf.kotlin.lite)
    implementation(libs.kotlinx.coroutines.android)

    implementation(project(":core:util"))
}
