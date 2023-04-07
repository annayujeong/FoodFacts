package com.example.foodfacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.foodfacts.databinding.FragmentSplashScreenBinding
import com.google.android.material.snackbar.Snackbar

class AuthenticationFragment : Fragment(), AuthenticationInterface {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginButton = binding.buttonAuthFragmentLogin

        val createAccountButton = binding.buttonAuthFragmentCreateAccount

        val email = binding.editTextAuthFragmentEmail

        val authViewModel: AuthViewModel by activityViewModels()

        val password = binding.editTextAuthFragmentPassword

        loginButton.setOnClickListener(){

            if(email.text.isBlank()){
                displayToastMessage("Email cannot be empty!")
            } else if (password.text.isBlank()){
                displayToastMessage("Password cannot be empty!")
            } else {
                authViewModel.signIn(email.text.toString(), password.text.toString(), this)
            }
        }

        createAccountButton.setOnClickListener(){
            findNavController().navigate(R.id.action_splashScreenFragment_to_createAccountFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun goToHomeScreen(){
        findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
    }

    override fun goToCreateAccount(){
        findNavController().navigate(R.id.action_splashScreenFragment_to_createAccountFragment)
    }

    override fun displayToastMessage(message : String) {
        this.view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG)
            .setAction("Dismiss"){
            }
            .show() }
    }
}