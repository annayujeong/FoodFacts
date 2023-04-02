package com.example.foodfacts


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
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
        fail: () -> Unit
    ) {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            getData(itemName, { navigateToResult(navController) }, fail)
        }

//        val scope = CoroutineScope(Dispatchers.Main)
//        scope.launch {
//            try {
//                val result = scope.async {
//                    genericLiveDataObject.value = apiRepository.getFoodApiResult(itemName)
//                }
//                result.await()
//                navigateToResult(navController)
//            } catch (_: Exception) {
//                success.invoke()
//            }
//        }
    }

    suspend fun getData(itemName: String, success: () -> Unit, fail: () -> Unit) {
        val scope = CoroutineScope(Dispatchers.Main)
        val process = scope.async {
            apiRepository.getFoodApiResult(itemName)
        }
        process.await()?.let {
            genericLiveDataObject.value = it
            success.invoke()
        } ?: run {
            fail.invoke()
        }
    }

    fun navigateToResult(navController: NavController) {
        navController.navigate(R.id.resultFragment)
    }

}
