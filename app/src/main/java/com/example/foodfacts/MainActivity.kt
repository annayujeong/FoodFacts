package com.example.foodfacts

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val cn = ComponentName(this, "org.tensorflow.lite.examples.imageclassification.MainActivity")
//        val intent = Intent().setComponent(cn)
//        startActivity(intent)

        val intent = Intent(this, org.tensorflow.lite.examples.imageclassification.MainActivity::class.java)
        //startActivity(intent)
    }
}