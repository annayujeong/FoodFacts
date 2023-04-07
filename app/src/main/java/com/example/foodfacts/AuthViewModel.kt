package com.example.foodfacts

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
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
}