package com.example.foodfacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.foodfacts.databinding.FragmentFoodItemDescriptionBinding
import com.example.foodfacts.databinding.FragmentFoodItemListBinding
import com.google.android.material.snackbar.Snackbar

class FoodItemDescriptionFragment : Fragment() {

    private var _binding: FragmentFoodItemDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodItemDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val foodViewModel: FoodViewModel by activityViewModels()

        val currFood =  arguments?.get("key")

        
        val foodName = binding.textViewFoodItemDescriptionName
        val foodProtein = binding.textViewFoodItemDescriptionProtein
        val foodFat = binding.textViewFoodItemDescriptionTotalFat
        val foodImage = binding.imageViewFoodItemDescription
        val foodCalories = binding.textViewFoodItemDescriptionCalories
        val foodServingQty = binding.textViewFoodItemDescriptionServingQty

        val deleteButton = binding.buttonFoodItemDescriptionDeleteFood

        val individualFoodItem = foodViewModel.getFoodListAlrInit().filter {x -> x.nbdNo == currFood}

        for (food in individualFoodItem) {
            foodName.text = food.foodName
            foodCalories.text = food.calories
            foodProtein.text = getString(R.string.food_protein, food.protein)
            foodFat.text = getString(R.string.food_fat, food.totalFat)
            foodServingQty.text = food.servingQty

            Glide.with(foodImage.context)
                .load(food.photoUrl)
                .into(foodImage)
        }

        deleteButton.setOnClickListener {
            foodViewModel.deleteFoodItem(currFood as String)

            this.view?.let { it1 ->
                Snackbar.make(it1, "Item deleted", Snackbar.LENGTH_LONG)
                    .setAction("Dismiss"){}.show()
            }
            findNavController().navigate(R.id.action_FoodItemDescriptionFragment_to_FoodItemListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
