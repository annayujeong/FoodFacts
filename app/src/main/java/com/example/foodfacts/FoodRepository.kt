package com.example.foodfacts

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class FoodRepository @Inject constructor(@ApplicationContext private var appContext: Context) {

    lateinit var db: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    //lateinit var currentUser: FirebaseUser

    val foodList = ArrayList<FoodItem>()

    fun getFoodList(callback: (foodList: List<FoodItem>) -> Unit) {
        db = Firebase.firestore
        auth = Firebase.auth
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

        val scope = CoroutineScope(Dispatchers.Main)

        foodList.clear()

        scope.launch {
            db.collection(uid.toString())
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val newFoodItem = FoodItem(
                            nbdNo = document.data["ndb_no"].toString(),
                            foodName = document.data["food_name"].toString(),
                            calories = document.data["nf_calories"].toString(),
                            protein = document.data["nf_protein"].toString(),
                            totalFat = document.data["nf_total_fat"].toString(),
                            photoUrl = document.data["photo"].toString(),
                            servingQty = document.data["serving_qty"].toString()
                        )
                        foodList.add(newFoodItem)
                    }

                    if (foodList.isNotEmpty()) {
                        callback.invoke(foodList)
                    }
                }
        }

    }

    fun deleteFoodItem(food_id: String) {
        db = Firebase.firestore
        auth = Firebase.auth
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch {
            db.collection(uid.toString())
                .document(food_id)
                .delete()
                .addOnSuccessListener {
                    Log.d("test", "delete success")
                }
        }
    }
}



