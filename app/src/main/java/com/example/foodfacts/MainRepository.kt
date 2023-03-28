package com.example.foodfacts

import android.content.Context
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

class MainRepository@Inject constructor(@ApplicationContext private var appContext: Context){ // TODO: use this context to call another activity
    suspend fun createHttpRequest(itemName: String): HashMap<String, String> { // TODO: refactor this function
        val scope = CoroutineScope(Dispatchers.Main)
        val request = scope.async {
            val client = HttpClient {
                install(ContentNegotiation) {
                    gson()
                }
            }
            val url = "https://trackapi.nutritionix.com/v2/natural/nutrients"
            val response = client.post(url) {
                headers {
                    append("x-app-id", "a644998d") // TODO: use env
                    append("x-app-key", "8097f84bafeb7ed6f04e7db575eeeb7c")
                    append("x-remote-user-id", "0")
                }
                setBody(FormDataContent(Parameters.build {
                    append("query", itemName)
                }))
            }
            return@async response
        }

        val getResultList = scope.async {
            val response = request.await()
            val foodJsonObject = response.body<JsonObject>().get("foods").asJsonArray.get(0).asJsonObject

            val resultMap = hashMapOf<String, String>() // TODO: use constants
            resultMap["food_name"] = foodJsonObject.get("food_name").toString()
            resultMap["nf_calories"] = foodJsonObject.get("nf_calories").toString()
            resultMap["nf_total_fat"] = foodJsonObject.get("nf_total_fat").toString()
            resultMap["nf_protein"] = foodJsonObject.get("nf_protein").toString()
            resultMap["nf_protein"] = foodJsonObject.get("photo").asJsonObject.get("thumb").toString()
            println("==== in model ====")
            println(resultMap)
            return@async resultMap
        }
        return getResultList.await()
    }

}
