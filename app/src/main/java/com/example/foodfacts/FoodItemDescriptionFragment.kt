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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FoodItemDescriptionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodItemDescriptionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentFoodItemDescriptionBinding? = null
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
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_food_item_description, container, false)

        _binding = FragmentFoodItemDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val foodViewModel: FoodViewModel by activityViewModels()

        val currQuest =  arguments?.get("key")

        //val foodName = view.findViewById<TextView>(R.id.textView_FoodItemDescription_name)
        val foodName = binding.textViewFoodItemDescriptionName
        //val foodProtein = view.findViewById<TextView>(R.id.textView_FoodItemDescription_protein)
        val foodProtein = binding.textViewFoodItemDescriptionProtein
        //val foodFat = view.findViewById<TextView>(R.id.textView_FoodItemDescription_total_fat)
        val foodFat = binding.textViewFoodItemDescriptionTotalFat
        //val foodImage = view.findViewById<ImageView>(R.id.imageView_FoodItemDescription)
        val foodImage = binding.imageViewFoodItemDescription
        //val foodCalories = view.findViewById<TextView>(R.id.textView_FoodItemDescription_calories)
        val foodCalories = binding.textViewFoodItemDescriptionCalories
        //val foodServingQty = view.findViewById<TextView>(R.id.textView_FoodItemDescription_serving_qty)
        val foodServingQty = binding.textViewFoodItemDescriptionServingQty

        //val deleteButton = view.findViewById<Button>(R.id.button_FoodItemDescription_deleteFood)
        val deleteButton = binding.buttonFoodItemDescriptionDeleteFood

        val individualFoodItem = foodViewModel.getFoodListAlrInit().filter {x -> x.nbdNo == currQuest}

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
            foodViewModel.deleteFoodItem(currQuest as String)

            this.view?.let { it1 ->
                Snackbar.make(it1, "Item deleted", Snackbar.LENGTH_LONG)
                    .setAction("Dismiss"){}.show()
            }
            findNavController().navigate(R.id.action_FoodItemDescriptionFragment_to_FoodItemListFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment foodItemDescriptionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FoodItemDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}