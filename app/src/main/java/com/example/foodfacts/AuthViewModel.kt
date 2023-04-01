package com.example.foodfacts

import android.util.Log
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

    fun initializeRepository(){
        mainRepository.initialize()
    }

    fun createAccount(email:String, password:String, listener: AuthenticationInterface){
        mainRepository.createAccount(email, password, listener)
    }

    fun signIn(email:String, password:String, listener: AuthenticationInterface){
        mainRepository.signIn(email, password, listener)
    }

    fun currentUser() : FirebaseUser?{
        return mainRepository.getUser()
    }
}