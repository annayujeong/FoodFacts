package com.example.foodfacts

import android.annotation.SuppressLint
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.tensorflow.lite.examples.imageclassification.fragments.CameraFragment
import org.tensorflow.lite.task.vision.classifier.Classifications

class AppCameraFragment: CameraFragment() {
    private val apiViewModel: ApiViewModel by activityViewModels()

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onResults(
        results: List<Classifications>?,
        inferenceTime: Long
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val dataObserver = Observer<String> { newData ->
                apiViewModel.getDataAndNavigateToResult(newData, findNavController()) {
                    temp() // TODO
                }
            }
            // TODO:
            //  1. when tapping back button: java.lang.NullPointerException: null cannot be cast to non-null type com.example.foodfacts.MainActivity
            //  2. ^ this is likeyly about this lifecycle, maybe observe this in upper class?
            apiViewModel.cameraScannedData.observe(activity as MainActivity, dataObserver)
        }
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

    private fun temp() {
        println("temp function")
    }
}
