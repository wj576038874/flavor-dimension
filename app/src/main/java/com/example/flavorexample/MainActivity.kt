package com.example.flavorexample

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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


    }
}