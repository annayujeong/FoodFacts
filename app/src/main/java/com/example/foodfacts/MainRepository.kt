package com.example.foodfacts

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(@ApplicationContext private var appContext: Context) {

    // reading/writing or fetching/posting data to remote
    // or local source

    lateinit var db: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    //lateinit var currentUser: FirebaseUser

    private var currentUser: FirebaseUser? = null

    var singletonTest = (0..10000000000000).random()

    fun initialize(){
        db = Firebase.firestore
        auth = Firebase.auth
    }

    fun createAccount(email : String, password : String, listener: AuthenticationInterface){
        //TODO: handling empty email or password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if(task.isSuccessful) {
                    // sign in successful
                    Log.d("Auth", "User account created")
                    currentUser = auth.currentUser
                    listener.goToHomeScreen()
                    createCollection()
                } else {
                    // sign in failed
                    Log.d("Auth", "User account creation failed", task.exception)
                    Toast.makeText(appContext, "Authentication failed!",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

//    fun createAccount(email : String, password : String){
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if(task.isSuccessful) {
//                    // sign in successful
//                    Log.d("Auth", "User account created")
//                    val user = auth.currentUser
//                } else {
//                    // sign in failed
//                    Log.d("Auth", "User account creation failed", task.exception)
//                    Toast.makeText(baseContext, "Authentication failed!",
//                        Toast.LENGTH_SHORT).show()
//                }
//            }
//    }

    fun signIn(email:String, password:String, listener: AuthenticationInterface){
        //TODO: handling empty email or password
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(){task ->
                if(task.isSuccessful){
                    Log.d("Auth", "Sign in with email successful")
                    currentUser = auth.currentUser
                    listener.goToHomeScreen()
                    createCollection()
                } else {
                    // sign in failed
                    Log.d("Auth", email)
                    Log.d("Auth", password)
                    Log.d("Auth", "Sign in with email failed", task.exception)
                    Toast.makeText(appContext, "Authentication failed",
                    Toast.LENGTH_SHORT).show()
                }
            }
    }

//    fun signIn(email:String, password:String){
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) {task ->
//                if(task.isSuccessful){
//                    // sign in
//                    Log.d("Auth", "Sign in with email successful")
//                    val user = auth.currentUser
//
//                    // TODO: go to home fragment
//                } else {
//                    // sign in failed
//                    Log.d("Auth", "Sign in with email failed")
//                    Toast.makeText(baseContext, "Authentication failed!",
//                        Toast.LENGTH_SHORT).show()
//                }
//            }
//    }

    // test method for creating a collection using the current user's uid
    fun createCollection(){
        val testData = mapOf<String, Int>("calories" to 500)
        currentUser?.let { db.collection(it.uid).document("apple").set(testData) }
    }

    fun addItem(foodInfo: HashMap<String, String>, callback: () -> Unit) {
        auth.currentUser?.let {
            db.collection(it.uid).document(hashItemKey(foodInfo)).set(foodInfo).addOnSuccessListener {
                Toast.makeText(appContext, "Item Saved", Toast.LENGTH_SHORT).show()
                callback.invoke()
            }
        }
    }

    private fun hashItemKey(foodInfo: HashMap<String, String>): String {
        val key = foodInfo[FoodConstants.NDB_NO]!!.toInt()
        val quantity = foodInfo[FoodConstants.QUANTITY]!!.toDouble()
        return (key * 100 + quantity * 5).toString()
    }

    fun didItemExist(ndbNumber: String, callback: () -> Unit) {
        auth.currentUser?.let {
            db.collection(it.uid).document(ndbNumber).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (task.result != null && document.exists()) {
                        callback.invoke()
                    }
                }
            }
        }
    }

    fun removeItem(){
        // TODO: remove item from user collection
    }

    fun getUser(): FirebaseUser? {
        return currentUser
    }
}