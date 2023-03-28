package com.example.foodfacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {

    // TODO: view model for handling authentication requests,
    //  logging in and signing out, and creating accounts

//    lateinit var db: FirebaseFirestore
//
//    private lateinit var auth: FirebaseAuth
//
//    private var currentUser: FirebaseUser? = null
//
//    var user: MutableLiveData<FirebaseUser> = MutableLiveData()

//    fun initialize(){
//        db = Firebase.firestore
//        auth = Firebase.auth
//    }

    fun createAccount(email:String, password:String, listener: AuthenticationInterface){
        mainRepository.initialize()
        mainRepository.createAccount(email, password, listener)
    }

    fun signIn(email:String, password:String, listener: AuthenticationInterface){
        mainRepository.initialize()
        mainRepository.signIn(email, password, listener)
    }

    fun currentUser() : FirebaseUser?{
        return mainRepository.getUser()
    }
}