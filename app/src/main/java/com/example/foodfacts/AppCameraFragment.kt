package com.example.foodfacts

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import org.tensorflow.lite.examples.imageclassification.fragments.CameraFragment
import org.tensorflow.lite.task.vision.classifier.Classifications

class AppCameraFragment: CameraFragment() {
    private val apiViewModel: ApiViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataObserver = Observer<String> { newData ->
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                if (newData != null) {
                    apiViewModel.getDataAndNavigateToResult(
                        newData,
                        findNavController(),
                    ) { onResultNotFound() }
                    apiViewModel.cameraScannedData.value = null
                }
            }
        }
        Handler(Looper.getMainLooper()).post {
            apiViewModel.cameraScannedData.observe(viewLifecycleOwner, dataObserver)
        }
    }

    @SuppressLint("NotifyDataSetChanged", "CommitTransaction")
    override fun onResults(
        results: List<Classifications>?,
        inferenceTime: Long
    ) {
        if (results?.size!! > 0) {
            results[0].categories?.let { categories ->
                if (categories.size > 0) {
                    apiViewModel.cameraScannedData.postValue(categories[0].label)
                }
            }
        }
    }

    private fun onResultNotFound() {
        reInit()
        displayToastMessage("Please scan a food item")
    }

    private fun reInit() {
        this.imageClassifierHelper.detected = false
    }

    private fun displayToastMessage(message : String) {
        this.view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG)
            .setAction("Dismiss"){}.show()
        }
    }

}
