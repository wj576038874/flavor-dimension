import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        //格式化apk输出文件名
        outputs.forEach { output ->
            if (output is ApkVariantOutputImpl) {
                output.outputFileName = "flavor-${buildType.name}-v${versionName}-${productFlavors[0].name}.apk"
            }
        }

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


/**
 * 打包上传ftp，然后通过webhook发送打钉钉
 */
project.afterEvaluate {
    tasks.forEach {
        if (it.name.startsWith("assemble")) {
            it.doLast {
                android.applicationVariants.all {
                    val buildTypeName = buildType.name
                    val flavorName = productFlavors[0].name
                    val flavorsUpperCase =
                        flavorName.substring(0, 1).uppercase() + flavorName.substring(1)
                    val buildTypeUpperCase =
                        buildTypeName.substring(0, 1).uppercase() + buildTypeName.substring(1)
                    val currentTaskName = "assemble$flavorsUpperCase$buildTypeUpperCase"
                    //当前任务才执行如下逻辑
                    if (this@doLast.name == currentTaskName) {
                        var outputFileName = "flavor-${buildTypeName}-v${versionName}-${flavorName}.apk"
                        outputs.forEach { output ->
                            if (output is ApkVariantOutputImpl) {
                                outputFileName = output.outputFileName
                                println("输出安装包名字为$outputFileName")
                            }
                        }

                        val apkDefaultFolder =
                            "${project.projectDir.path}/build/outputs/apk/${flavorName}/${buildTypeName}"
                        println("原始生成apk路径$apkDefaultFolder")
                        //打包时间
                        val time = SimpleDateFormat("yyyyMMdd.HHmm", Locale.CANADA).format(Date().time)
                        // 定义目标文件夹
                        val destFolder = File("${rootProject.projectDir.path}/apk")
                        println("目标apk文件路径$destFolder")
                        try {
                            if (!destFolder.exists()) {
                                destFolder.mkdirs()
                            }
                        } catch (e: Exception) {
                            println(e)
                        }
                        val newFileName = "flavor-${buildTypeName}-v${versionName}-${flavorName}-$time.apk"
                        copy {
                            from("$apkDefaultFolder/$outputFileName")
                            into(destFolder)
                            rename(outputFileName, newFileName)
                        }
                        println("上传apk文件到ftp 获取安装包下载链接")
                        println("通过webhook然后发送消息到飞书")
                    }
                }
            }
        }
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