plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.flavorexample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.flavorexample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "arm", "\"arm64-release\"")
        }
        create("debugDev") {

        }
        debug {
            applicationIdSuffix = ".new"
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            //这里定义的值会覆盖productFlavors中定义的值
//            manifestPlaceholders["CHANNEL"] = "china-buildTypes-debug"
//            buildConfigField("String", "arm" , "\"arm64-debug\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
    }

    flavorDimensions.add("channel")
//    flavorDimensions.add("server")
    productFlavors {
        create("kenya") {
            dimension = "channel"
            applicationIdSuffix = ".kenya"
            manifestPlaceholders["CHANNEL"] = "kenya"
            manifestPlaceholders["label"] = "kenya"
            buildConfigField("String", "arm", "\"arm64-kenya-debug\"")
        }
        create("china") {
            dimension = "channel"
            applicationIdSuffix = ".china"
            manifestPlaceholders["CHANNEL"] = "china"
            manifestPlaceholders["label"] = "china"
            buildConfigField("String", "arm", """"arm64-china-debug"""")
        }
        create("uganda") {
            dimension = "channel"
            applicationIdSuffix = ".uganda"
            manifestPlaceholders["CHANNEL"] = "uganda"
            manifestPlaceholders["label"] = "uganda"
            buildConfigField("String", "arm", "\"arm64-uganda-debug\"")
        }
        create("mexico"){
            dimension = "channel"
            applicationIdSuffix = ".mexico"
            manifestPlaceholders["CHANNEL"] = "mexico"
            manifestPlaceholders["label"] = "mexico"
            buildConfigField("String", "arm", "\"arm64-uganda-debug\"")
        }
//        create("cc"){
//            dimension = "server"
//        }
//        create("dd"){
//            dimension = "server"
//        }
    }

    android.applicationVariants.configureEach {
        //打包任务分组
        println("####$flavorName ${buildType.name}")
        val buildType = buildType.name
        val flavors = productFlavors[0].name
        val buildTypeUpperCase =
            buildType.substring(0, 1).uppercase() + buildType.substring(1)
        val flavorsUpperCase =
            flavors.substring(0, 1).uppercase() + flavors.substring(1)
        val currentTaskName = "assemble${flavorsUpperCase}${buildTypeUpperCase}"
        println("####$currentTaskName")
//        tasks.getByName(currentTaskName).group = "packApkTask"
        tasks.named(currentTaskName).get().group = "apkpack-$buildType"
    }

}

val debugDevImplementation = configurations.getByName("debugDevImplementation")


dependencies {
    debugImplementation(libs.gson)
    debugApi(libs.gson)
    "debugDevImplementation"(libs.glide)
    debugDevImplementation(libs.glide)
    debugDevImplementation(libs.gson)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}