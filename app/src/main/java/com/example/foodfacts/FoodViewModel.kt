package com.example.foodfacts

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// ViewModel responsible for handling calls/other stuff to the Firebase database
// handling user information and account?

@HiltViewModel
class FoodViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {
//class FoodViewModel: ViewModel(){
    // TODO: sign in and account creation here?

    lateinit var db: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    private var currentUser: FirebaseUser? = null

    fun getRandom() {
        Log.d("Testing", mainRepository.singletonTest.toString())
    }

    fun initialize() {
        db = Firebase.firestore
        auth = Firebase.auth
    }

    fun createAccount() {

    }

    fun signIn() {
        // TODO:
    }

    fun getFavouriteItems() {
        if (currentUser != null) {
            // TODO:
        }
    }

    fun addToFavouriteItems(foodInfo: HashMap<String, String>) {
        mainRepository.addItem(foodInfo)
    }

    fun checkItemExist(ndbNumber: String, callback: () -> Unit) {
        mainRepository.didItemExist(ndbNumber, callback)
    }
}
