package com.example.foodfacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfacts.databinding.FragmentFoodItemListBinding
import com.example.foodfacts.databinding.FragmentResultBinding

class FoodItemListFragment : Fragment(), NavigateToFoodDescriptionListener {

    private var _binding: FragmentFoodItemListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFoodItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun setUpRecyclerView(foodItems: List<FoodItem>) {
        val adapter = FoodItemAdapter(foodItems, this)
        val foodRecyclerView = binding.recyclerViewFoodFragmentList
        foodRecyclerView.adapter = adapter
        foodRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val foodViewModel:FoodViewModel by activityViewModels()
        foodViewModel.getFavouriteItems() {
            setUpRecyclerView(it)
        }
    }

    override fun onNavigateToFoodDescription(foodItem: FoodItem) {
        val bundle = bundleOf("key" to foodItem.nbdNo)
        findNavController().navigate(R.id.action_foodItemListFragment_to_foodItemDescriptionFragment, bundle)
    }
}