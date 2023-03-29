package com.example.foodfacts

import android.content.Context
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject
import kotlin.collections.HashMap

class MainRepository@Inject constructor(@ApplicationContext private var appContext: Context){

    suspend fun getFoodApiResult(itemName: String): HashMap<String, String> {
        val scope = CoroutineScope(Dispatchers.Main)
        val request = scope.async {
            val client = HttpClient {
                install(ContentNegotiation) {
                    gson()
                }
            }
            val response = client.post(ApiConstants.API_URL) {
                headers {
                    append(ApiConstants.APP_ID, BuildConfig.NUTRITIONSIX_API_ID)
                    append(ApiConstants.APP_KEY, BuildConfig.NUTRITIONSIX_API_KEY)
                }
                setBody(FormDataContent(Parameters.build {
                    append(ApiConstants.QUERY, itemName)
                }))
            }
            return@async response
        }
        val getResultList = scope.async {
            val response = request.await()
            val foodJsonObject = response.body<JsonObject>().get(FoodConstants.FOODS).asJsonArray.get(0).asJsonObject
            return@async putJsonObjectInHashMap(foodJsonObject)
        }
        return getResultList.await()
    }

    private fun putJsonObjectInHashMap(foodJsonObject: JsonObject): HashMap<String, String> {
        val resultMap = hashMapOf<String, String>()
        resultMap[FoodConstants.QUANTITY] = formatJsonElementToString(foodJsonObject.get(FoodConstants.QUANTITY))
        resultMap[FoodConstants.NDB_NO] = formatJsonElementToString(foodJsonObject.get(FoodConstants.NDB_NO))
        resultMap[FoodConstants.NAME] = formatJsonElementToString(foodJsonObject.get(FoodConstants.NAME))
        resultMap[FoodConstants.CALORIES] = formatJsonElementToString(foodJsonObject.get(FoodConstants.CALORIES))
        resultMap[FoodConstants.TOTAL_FAT] = formatJsonElementToString(foodJsonObject.get(FoodConstants.TOTAL_FAT))
        resultMap[FoodConstants.PROTEIN] = formatJsonElementToString(foodJsonObject.get(FoodConstants.PROTEIN))
        resultMap[FoodConstants.IMAGE_URL] = formatJsonElementToString(foodJsonObject.get(FoodConstants.IMAGE_URL).asJsonObject.get(FoodConstants.THUMBNAIL))
        return resultMap
    }

    fun didSingleItemEntered(returnedData: HashMap<String, String>): Boolean {
        val quantity = returnedData[FoodConstants.QUANTITY]!!.toInt()
        return quantity == 1
    }

    private fun formatJsonElementToString(jsonElement: JsonElement): String {
        return jsonElement.toString().replace("\"", "")
    }

}
