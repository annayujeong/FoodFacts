package com.example.foodfacts

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodfacts.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiViewModel: ApiViewModel by activityViewModels()
        val foodViewModel: FoodViewModel by activityViewModels()

        var returnedData = HashMap<String, String>()
        val dataObserver = Observer<HashMap<String, String>> { newData ->
            if (newData != null) {
                returnedData = newData
                updateViewsOnNewData(foodViewModel, returnedData)
            }
        }
        apiViewModel.genericLiveDataObject.observe(viewLifecycleOwner, dataObserver)

        binding.buttonSaveResult.setOnClickListener {
            foodViewModel.addToFavouriteItems(returnedData) {
                disableSaveButton()
            }
        }
    }

    private fun disableSaveButton() {
        binding.buttonSaveResult.isEnabled = false
        binding.buttonSaveResult.isClickable = false
        binding.buttonSaveResult.setBackgroundColor(Color.GRAY)
        binding.buttonSaveResult.setTextColor(Color.WHITE)
        binding.buttonSaveResult.text = "Saved"
    }

    private fun updateViewsOnNewData(foodViewModel: FoodViewModel, returnedData: HashMap<String, String>) {
        foodViewModel.checkItemExist(returnedData[FoodConstants.NDB_NO]!!) {
            disableSaveButton()
        }
        Glide.with(binding.imageViewResult).load(returnedData[FoodConstants.IMAGE_URL]).into(binding.imageViewResult)
        binding.textViewNameResult.text = returnedData[FoodConstants.NAME]
        binding.textViewFatResultValue.text = returnedData[FoodConstants.TOTAL_FAT]
        binding.textViewCaloriesResultValue.text = returnedData[FoodConstants.CALORIES]
        binding.textViewProteinResultValue.text = returnedData[FoodConstants.PROTEIN]
        binding.textViewQuantityResultValue.text = returnedData[FoodConstants.QUANTITY]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
