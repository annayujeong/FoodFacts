package com.example.foodfacts


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel() {
    var cameraScannedData: MutableLiveData<String> = MutableLiveData<String>()
    var genericLiveDataObject: MutableLiveData<HashMap<String, String>> = MutableLiveData<HashMap<String, String>>()

    fun getDataAndNavigateToResult(
        itemName: String,
        navController: NavController,
        callback:() -> Unit)
    {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            try {
                println("==== apiviewmodel ******")
                println(itemName)
                val temp = scope.async {
                    genericLiveDataObject.value = apiRepository.getFoodApiResult(itemName)
                }
                temp.await()
                navController.navigate(R.id.resultFragment)
            } catch (_: Exception) {
                callback.invoke()
            }
        }
    }

}
