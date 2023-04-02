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
        callback:() -> Unit
    ) {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            val result = apiRepository.getFoodApiResult(itemName)
            if (result != null) {
                genericLiveDataObject.value = apiRepository.getFoodApiResult(itemName)
                navController.navigate(R.id.action_homeFragment_to_resultFragment)
            } else {
                callback.invoke()
            }
//            try {
//                val temp = scope.async {
//                    genericLiveDataObject.value = apiRepository.getFoodApiResult(itemName)
//                }
//                temp.await()
//                navigateToResult(navController)
//            } catch (_: Exception) {
//                callback.invoke()
//            }
        }
    }

    suspend fun getData(itemName: String, callback: () -> Unit) {
        val scope = CoroutineScope(Dispatchers.Main)
        val process = scope.async {
            println("11111111")
            apiRepository.getFoodApiResult(itemName)
        }
        println("2222222")
        process.await()?.let {
            genericLiveDataObject.value = it
            println("getData true!!!!")
            println("3333333")
            callback.invoke()
        }
        println("44444")
    }

    fun navigateToResult(navController: NavController) {
        navController.navigate(R.id.resultFragment)
    }
}
