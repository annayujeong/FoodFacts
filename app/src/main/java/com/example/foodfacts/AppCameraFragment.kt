package com.example.foodfacts

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.tensorflow.lite.examples.imageclassification.databinding.FragmentCameraBinding
import org.tensorflow.lite.examples.imageclassification.fragments.CameraFragment
import org.tensorflow.lite.task.vision.classifier.Classifications

class AppCameraFragment: CameraFragment() {
    private val apiViewModel: ApiViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged", "CommitTransaction")
    override fun onResults(
        results: List<Classifications>?,
        inferenceTime: Long
    ) {
        if (results?.size!! > 0) {
            results[0].categories?.let { categories ->
                if (categories.size > 0) {
                    println("==== value ====")
                    println(categories[0].label)
                    apiViewModel.cameraScannedData.postValue(categories[0].label)
                }
            }
        }
    }

}
