package com.example.foodfacts

import android.widget.TextView
import android.widget.Toast
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
class ApiViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel() {
    var genericLiveDataObject: MutableLiveData<HashMap<String, String>> = MutableLiveData<HashMap<String, String>>()

    fun updateDataWithLiveData(itemName: String) {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            handleReturnedData(apiRepository.getFoodApiResult(itemName))
        }
    }

    private fun handleReturnedData(returnedData: HashMap<String, String>) {
        if (apiRepository.didSingleItemEntered(returnedData)) {
            genericLiveDataObject.value = returnedData
        } else {
            apiRepository.pushToastIfNotSingleItem()
        }
    }
}