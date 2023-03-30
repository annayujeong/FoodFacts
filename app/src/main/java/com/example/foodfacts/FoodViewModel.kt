package com.example.foodfacts

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel responsible for handling calls/other stuff to the Firebase database
// handling user information and account?

@HiltViewModel
class FoodViewModel @Inject constructor(private val foodRepository: FoodRepository) : ViewModel() {

    // TODO: sign in and account creation here?

    fun initialize(){
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            foodRepository.initialize()
        }
    }

    fun returnFoodItemList(): ArrayList<FoodItem> {
        return foodRepository.foodList
    }

    fun createAccount(){

    }

    fun signIn(){
        // TODO:
    }


}