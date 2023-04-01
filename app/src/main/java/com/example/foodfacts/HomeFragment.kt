package com.example.foodfacts

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.View.OnAttachStateChangeListener
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.foodfacts.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        val apiViewModel: ApiViewModel by activityViewModels()

        val button = view.findViewById<Button>(R.id.button_home)
        val foodButton = view.findViewById<Button>(R.id.button_listItem_GoToList)

        val text = view.findViewById<TextView>(R.id.textView_food_name_home).text.toString()

        foodButton.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_foodItemListFragment)
        }

        val search = view.findViewById<SearchView>(R.id.searchView)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                apiViewModel.getDataAndNavigateToResult(search.query.toString(), findNavController())
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        button.setOnClickListener {
            apiViewModel.getDataAndNavigateToResult(text, findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
