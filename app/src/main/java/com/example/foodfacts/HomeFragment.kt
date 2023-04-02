package com.example.foodfacts

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.foodfacts.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        val apiViewModel: ApiViewModel by activityViewModels()

        val button = binding.buttonHomeFragmentSearch

        val foodButton = binding.buttonHomeFragmentFavourites

        foodButton.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_foodItemListFragment)
        }

        val search = binding.searchViewHomeFragment
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                search.clearFocus()
                println("000000 setOnQueryTextListener")
                apiViewModel.getDataAndNavigateToResult(search.query.toString(),
                    findNavController()){displayToastMessage("Please enter a food item")}
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        button.setOnClickListener {
            apiViewModel.getDataAndNavigateToResult(search.query.toString(), findNavController()){
                displayToastMessage("Please enter a food item")
            }
        }
//        val cameraFragment = AppCameraFragment()

        val cameraButton = view.findViewById<Button>(R.id.button_camera)
        cameraButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_appCameraFragment)
        }

//        val dataObserver = Observer<String> { newData ->
//            println("000000 dataObserver")
//            apiViewModel.getDataAndNavigateToResult(newData, findNavController()) {
//                displayToastMessage("Error")
//            }
//        }
//        apiViewModel.cameraScannedData.observe(viewLifecycleOwner, dataObserver)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun displayToastMessage(message : String) {
        this.view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG)
            .setAction("Dismiss"){
            }
            .show() }
    }
}
