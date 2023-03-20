package com.example.foodfacts

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val cn = ComponentName(this, "org.tensorflow.lite.examples.imageclassification.MainActivity")
//        val intent = Intent().setComponent(cn)
//        startActivity(intent)

        val intent = Intent(this, org.tensorflow.lite.examples.imageclassification.MainActivity::class.java)
        //startActivity(intent)

        // initializing Firebase
        auth = Firebase.auth
    }

    // TODO: is checking if user already signed in necessary?
    override fun onStart(){
        super.onStart()
        // check if user is signed in, and act accordingly
        val currentUser = auth.currentUser
        if(currentUser != null){
            //reload()
            // TODO: display the corresponding fragment
        }
    }

    fun createAccount(email : String, password : String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    // sign in successful
                    Log.d("Auth", "User account created")
                    val user = auth.currentUser
                } else {
                    // sign in failed
                    Log.d("Auth", "User account creation failed", task.exception)
                    Toast.makeText(baseContext, "Authentication failed!",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signIn(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful){
                    // sign in
                    Log.d("Auth", "Sign in with email successful")
                    val user = auth.currentUser

                    // TODO: go to home fragment
                } else {
                    // sign in failed
                    Log.d("Auth", "Sign in with email failed")
                    Toast.makeText(baseContext, "Authentication failed!",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}