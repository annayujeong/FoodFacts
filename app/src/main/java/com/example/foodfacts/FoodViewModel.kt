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
class FoodViewModel @Inject constructor(private val mainRepository: MainRepository, private val foodRepository: FoodRepository): ViewModel() {

    fun getFavouriteItems(callback: (foodList: List<FoodItem>) -> Unit){
        foodRepository.getFoodList(callback)
    }

    fun getFoodListAlrInit(): List<FoodItem> {
        return foodRepository.foodList
    }

    fun deleteFoodItem(food_id: String) {
        foodRepository.deleteFoodItem(food_id)
    }


    fun addToFavouriteItems(foodInfo: HashMap<String, String>, callback: () -> Unit) {
        mainRepository.addItem(foodInfo, callback)
    }

    fun checkItemExist(ndbNumber: String, callback: () -> Unit) {
        mainRepository.didItemExist(ndbNumber, callback)
    }

}
