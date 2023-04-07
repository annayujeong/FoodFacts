package com.example.foodfacts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.foodfacts.databinding.FragmentCreateAccountBinding
import com.google.android.material.snackbar.Snackbar

class CreateAccountFragment : Fragment(), AuthenticationInterface {

    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authViewModel: AuthViewModel by activityViewModels()

        val button = binding.buttonCreateAccountFragmentCreate

        val email = binding.editTextCreateAccountFragmentEmail

        val password = binding.editTextCreateAccountFragmentPassword

        button.setOnClickListener{

            if(email.text.isBlank()){
                displayToastMessage("Email cannot be empty!")
            } else if (password.text.isBlank()){
                displayToastMessage("Password cannot be empty!")
            } else {
                authViewModel.createAccount(email.text.toString(), password.text.toString(), this)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun goToHomeScreen() {
        findNavController().navigate(R.id.action_createAccountFragment_to_homeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun displayToastMessage(message : String) {
        this.view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG)
            .setAction("Dismiss"){
            }
            .show() }
    }
}