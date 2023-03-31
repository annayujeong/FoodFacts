package com.example.foodfacts


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel() {
    var genericLiveDataObject: MutableLiveData<HashMap<String, String>> = MutableLiveData<HashMap<String, String>>()

    fun getDataAndNavigateToResult(itemName: String, navController: NavController) {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            val result = apiRepository.getFoodApiResult(itemName)
            if (result != null) {
                genericLiveDataObject.value = apiRepository.getFoodApiResult(itemName)
                navController.navigate(R.id.action_homeFragment_to_resultFragment)
            } else {
                apiRepository.pushToast("Please enter a food item")
            }
        }
    }

}
