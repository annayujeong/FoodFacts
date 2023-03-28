package com.example.foodfacts

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val authViewModel:AuthViewModel by viewModels()

        //val cn = ComponentName(this, "org.tensorflow.lite.examples.imageclassification.MainActivity")
        //val intent = Intent().setComponent(cn)
        //startActivity(intent)

        //val intent = Intent(this, org.tensorflow.lite.examples.imageclassification.MainActivity::class.java)
        //startActivity(intent)
    }

    // TODO: is checking if user already signed in necessary?
}