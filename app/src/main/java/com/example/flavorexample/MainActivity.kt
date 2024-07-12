package com.example.flavorexample

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val appInfo = packageManager.getApplicationInfo(packageName , 0)


        findViewById<TextView>(R.id.tv1).text = BuildConfig.APPLICATION_ID

        findViewById<TextView>(R.id.tv2).text = packageManager.getApplicationLabel(appInfo)


        val appInfo2 = packageManager.getApplicationInfo(packageName ,  PackageManager.GET_META_DATA)
        findViewById<TextView>(R.id.tv3).text = appInfo2.metaData?.getString("CHANNEL")

        findViewById<TextView>(R.id.tv4).text = BuildConfig.arm

        /**
         *     buildTypes {
         *         release {
         *             isMinifyEnabled = false
         *             proguardFiles(
         *                 getDefaultProguardFile("proguard-android-optimize.txt"),
         *                 "proguard-rules.pro"
         *             )
         *             buildConfigField("String", "arm" , "\"arm64-release\"")
         *         }
         *         create("debugDev"){
         *              isMinifyEnabled = false
         *         }
         *         debug {
         *             isMinifyEnabled = false
         *             proguardFiles(
         *                 getDefaultProguardFile("proguard-android-optimize.txt"),
         *                 "proguard-rules.pro"
         *             )
         *         }
         *     }
         * 跟buildTypes有关系 和flavor没关系 buildTypes默认自带debug和release
         * 所以可以直接使用 debugImplementation releaseImplementation debugApi releaseApi debugXxx... releaseXxx...
         * 如果新增 其他构建模式比如 debugDev 那么依赖需要用 字符串包裹，buildType+依赖
         * "debugDevImplementation"(libs.glide)
         * "debugDevApi"(libs.glide)
         * "debugDevXxx..."(libs.glide)
         * [可参照官网说明](https://developer.android.com/build/build-variants?hl=zh-cn#dependencies)
         *
         */
//        Glide.with(this)
        //debugImplementation(libs.gson) 切换到debug构建 才可以访问Gson()
        //"debugDevImplementation"(libs.glide) 切换到debugDev 才可以访问Glide.with(this)
    }
}