package com.example.foodfacts

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
        val dataObserver = Observer<HashMap<String, String>> { newData ->
            println("===== result fragment =====")
            println(newData)

            Glide.with(binding.imageViewResult).load(newData[FoodConstants.IMAGE_URL]).into(binding.imageViewResult)
            binding.textViewNameResult.text = newData[FoodConstants.NAME]
            binding.textViewFatResult.text = newData[FoodConstants.TOTAL_FAT]
            binding.textViewCaloriesResult.text = newData[FoodConstants.CALORIES]
            binding.textViewProteinResult.text = newData[FoodConstants.PROTEIN]
        }
        apiViewModel.genericLiveDataObject.observe(viewLifecycleOwner, dataObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
