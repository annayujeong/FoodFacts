package com.example.foodfacts

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface GenericDataListener {
    var value: HashMap<String, String>
}

@HiltViewModel
class ApiViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {
    var genericLiveDataObject: MutableLiveData<HashMap<String, String>> = MutableLiveData<HashMap<String, String>>()

    fun updateDataWithLiveData(itemName: String, errorTextView: TextView) {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            handleReturnedData(mainRepository.getFoodApiResult(itemName), errorTextView)
        }
    }

    private fun handleReturnedData(returnedData: HashMap<String, String>, errorTextView: TextView) {
        if (mainRepository.didSingleItemEntered(returnedData)) {
            genericLiveDataObject.value = returnedData
        } else {
            errorTextView.text = "Enter a single item!"
            println("quantity is not 1") // TODO: remove
        }
    }
}