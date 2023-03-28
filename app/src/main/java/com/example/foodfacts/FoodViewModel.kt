package com.example.foodfacts

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// ViewModel responsible for handling calls/other stuff to the Firebase database
// handling user information and account?

class FoodViewModel: ViewModel() {

    // TODO: sign in and account creation here?

    lateinit var db: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    private var currentUser: FirebaseUser? = null

    fun initialize(){
        db = Firebase.firestore
        auth = Firebase.auth
    }

    fun createAccount(){

    }

    fun signIn(){
        // TODO:
    }

    fun getFavouriteItems(){
        if(currentUser != null){
            // TODO:
        }
    }

    fun addToFavouriteItems(){
        if(currentUser != null){
            // TODO:
        }
    }

}