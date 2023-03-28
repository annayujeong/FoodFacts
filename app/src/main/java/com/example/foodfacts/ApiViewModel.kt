package com.example.foodfacts

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.statement.*
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

    lateinit var genericDataListener: GenericDataListener

    fun goToResultActivity(appContext: Context, itemName: String) {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            val intent = Intent(appContext, ResultActivity::class.java)
            intent.putExtra("RESULT_ITEM", mainRepository.createHttpRequest(itemName))

            appContext.startActivity(intent)
        }
    }
}