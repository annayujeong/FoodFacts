package com.example.foodfacts

import android.content.Context
import android.widget.Toast
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

class ApiRepository@Inject constructor(@ApplicationContext private var appContext: Context){

    suspend fun getFoodApiResult(itemName: String): HashMap<String, String>? {
        println("ApiRepository getFoodApiResult itemname " + itemName)
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
            val foodJsonObject: JsonObject? = try {
                response.body<JsonObject>().get(FoodConstants.FOODS).asJsonArray.get(0).asJsonObject
            } catch (e: NullPointerException) {
                null
            }
            return@async putJsonObjectInHashMap(foodJsonObject)
        }
        println("1.555555 : ")
        return getResultList.await()
    }

    private fun putJsonObjectInHashMap(foodJsonObject: JsonObject?): HashMap<String, String>? {
        val resultMap = hashMapOf<String, String>()
        if (foodJsonObject == null) {
            return null
        }
        resultMap[FoodConstants.NDB_NO] = hashItemKey(foodJsonObject)
        resultMap[FoodConstants.QUANTITY] = formatJsonElementToString(foodJsonObject.get(FoodConstants.QUANTITY))
        resultMap[FoodConstants.NAME] = formatJsonElementToString(foodJsonObject.get(FoodConstants.NAME))
        resultMap[FoodConstants.CALORIES] = formatJsonElementToString(foodJsonObject.get(FoodConstants.CALORIES))
        resultMap[FoodConstants.TOTAL_FAT] = formatJsonElementToString(foodJsonObject.get(FoodConstants.TOTAL_FAT))
        resultMap[FoodConstants.PROTEIN] = formatJsonElementToString(foodJsonObject.get(FoodConstants.PROTEIN))
        resultMap[FoodConstants.IMAGE_URL] = formatJsonElementToString(foodJsonObject.get(FoodConstants.IMAGE_URL).asJsonObject.get(FoodConstants.HIGH_RES))
        println("0000000 resultmap: " + resultMap.toString())
        return resultMap
    }

    private fun formatJsonElementToString(jsonElement: JsonElement): String {
        return jsonElement.toString().replace("\"", "")
    }

    fun pushToast(message: String) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun hashItemKey(foodInfo: JsonObject): String {
        val key = foodInfo.get(FoodConstants.NDB_NO).toString().toInt()
        val quantity = foodInfo.get(FoodConstants.QUANTITY).toString().toDouble()
        return (key * 100 + quantity * 5).toString()
    }

}
