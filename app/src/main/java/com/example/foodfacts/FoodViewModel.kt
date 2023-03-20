package com.example.foodfacts

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// ViewModel responsible for handling calls/other stuff to the Firebase database
// handling user information and account?

class FoodViewModel: ViewModel() {

    // TODO: sign in and account creation here?

    lateinit var db: FirebaseFirestore

    fun initializeFirebase(){
        db = Firebase.firestore
    }

    fun createAccount(){
        // TODO:
    }

    fun signIn(){
        // TODO:
    }

    fun getFavouriteItems(){
        // TODO:
    }

    fun addToFavouriteItems(){
        // TODO:
    }

}