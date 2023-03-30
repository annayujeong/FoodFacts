package com.example.foodfacts

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        // setup result fragment binder
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        // inflate the layout for this fragment
        return binding.root
//        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiViewModel: ApiViewModel by activityViewModels()
        val foodViewModel: FoodViewModel by activityViewModels()

        var returnedData = HashMap<String, String>()
        var ndbNumber: String
        var newDataFlag = false

        val dataObserver = Observer<HashMap<String, String>> { newData ->
            returnedData = newData
            ndbNumber = returnedData[FoodConstants.NDB_NO]!!

            foodViewModel.checkItemExist(ndbNumber) { disableButtonIfItemExist() }

            Glide.with(binding.imageViewResult).load(returnedData[FoodConstants.IMAGE_URL]).into(binding.imageViewResult)
            binding.textViewNameResult.text = returnedData[FoodConstants.NAME]
            binding.textViewFatResult.text = returnedData[FoodConstants.TOTAL_FAT]
            binding.textViewCaloriesResult.text = returnedData[FoodConstants.CALORIES]
            binding.textViewProteinResult.text = returnedData[FoodConstants.PROTEIN]

            newDataFlag = true
        }
        apiViewModel.genericLiveDataObject.observe(viewLifecycleOwner, dataObserver)

        binding.buttonSaveResult.setOnClickListener {
            if (newDataFlag) {
                println("==== ResultFragment ==== save button clicked")
                foodViewModel.addToFavouriteItems(returnedData)
                newDataFlag = false // TODO: will this persist (lifecycle)?
            }
        }

    }

    private fun disableButtonIfItemExist() {
        binding.buttonSaveResult.isEnabled = false
        binding.buttonSaveResult.isClickable = false
        binding.buttonSaveResult.setBackgroundColor(Color.GRAY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
