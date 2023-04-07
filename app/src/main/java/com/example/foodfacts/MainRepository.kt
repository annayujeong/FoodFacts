package com.example.foodfacts

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
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

    lateinit var db: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    private var currentUser: FirebaseUser? = null

    fun initialize(){
        db = Firebase.firestore
        auth = Firebase.auth
    }

    fun createAccount(email : String, password : String, listener: AuthenticationInterface){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if(task.isSuccessful) {
                    // sign in successful
                    Log.d("Auth", "User account created")
                    currentUser = auth.currentUser
                    listener.goToHomeScreen()
                } else {
                    // sign in failed
                    Log.d("Auth", "User account creation failed", task.exception)
                    if(task.exception is FirebaseAuthInvalidCredentialsException){
                        listener.displayToastMessage("Incorrect email format!")
                    } else if (task.exception is FirebaseAuthWeakPasswordException){
                        listener.displayToastMessage("Password should be at least 6 characters")
                    } else if (task.exception is FirebaseNetworkException) {
                        listener.displayToastMessage("Error connecting to the database")
                    }
                }
            }
    }

    fun signIn(email:String, password:String, listener: AuthenticationInterface){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(){task ->
                if(task.isSuccessful){
                    Log.d("Auth", "Sign in with email successful")
                    currentUser = auth.currentUser
                    listener.goToHomeScreen()
                } else {
                    // sign in failed
                    Log.d("Auth", "User account creation failed", task.exception)
                    if(task.exception is FirebaseAuthInvalidCredentialsException){
                        listener.displayToastMessage("Incorrect email format!")
                    } else if (task.exception is FirebaseAuthInvalidUserException){
                        listener.displayToastMessage("This user doesn't appear to exist!")
                    } else if (task.exception is FirebaseNetworkException) {
                        listener.displayToastMessage("Error connecting to the database")
                    }
                }
            }
    }

    fun addItem(foodInfo: HashMap<String, String>, callback: () -> Unit) {
        auth.currentUser?.let {
            db.collection(it.uid).document(foodInfo[FoodConstants.NDB_NO]!!).set(foodInfo).addOnSuccessListener {
                Toast.makeText(appContext, "Item Saved", Toast.LENGTH_SHORT).show()
                callback.invoke()
            }
        }
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
}
