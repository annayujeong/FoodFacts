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

class ResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiViewModel: ApiViewModel by activityViewModels()
        val dataObserver = Observer<HashMap<String, String>> { newData ->
            println("===== result fragment =====")
            println(newData)

            val resultImageView = view.findViewById<ImageView>(R.id.imageView_result)
            Glide.with(resultImageView).load(newData[FoodConstants.IMAGE_URL]).into(resultImageView)
            view.findViewById<TextView>(R.id.textView_name_result).text = newData[FoodConstants.NAME]
            view.findViewById<TextView>(R.id.textView_fat_result).text = newData[FoodConstants.TOTAL_FAT]
            view.findViewById<TextView>(R.id.textView_calories_result).text = newData[FoodConstants.CALORIES]
            view.findViewById<TextView>(R.id.textView_protein_result).text = newData[FoodConstants.PROTEIN]
        }
        apiViewModel.genericLiveDataObject.observe(viewLifecycleOwner, dataObserver)
    }

}
