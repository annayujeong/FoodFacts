package com.example.foodfacts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.foodfacts.databinding.FragmentCreateAccountBinding
import com.example.foodfacts.databinding.FragmentSplashScreenBinding
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AuthenticationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AuthenticationFragment : Fragment(), AuthenticationInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val loginButton = view.findViewById<Button>(R.id.loginButton)
        val loginButton = binding.buttonAuthFragmentLogin

        //val createAccountButton = view.findViewById<Button>(R.id.createAccountButton)
        val createAccountButton = binding.buttonAuthFragmentCreateAccount

        //val email = view.findViewById<EditText>(R.id.editTextTextEmailAddress)
        val email = binding.editTextAuthFragmentEmail

        val authViewModel: AuthViewModel by activityViewModels()

        //val password = view.findViewById<EditText>(R.id.editTextTextPassword)
        val password = binding.editTextAuthFragmentPassword

        loginButton.setOnClickListener(){
            authViewModel.signIn(email.text.toString(), password.text.toString(), this)
//            if(authViewModel.currentUser() != null){
//                findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
//            }
        }

        createAccountButton.setOnClickListener(){
            findNavController().navigate(R.id.action_splashScreenFragment_to_createAccountFragment)
//            authViewModel.createAccount(email.text.toString(), password.text.toString(), this)
//            Log.d("bruh", email.toString())
//            Log.d("bruh", password.toString())
//            Log.d("bruh", "create account button was clicked")

//            if(authViewModel.currentUser() != null){
//                findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
//            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment splashScreenFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AuthenticationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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