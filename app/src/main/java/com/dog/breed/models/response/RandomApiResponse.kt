package com.dog.breed.models.response

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class RandomApiResponse : BaseApiResponse() {
    var image: String = ""
    class RandomApiDeserializer : JsonDeserializer<RandomApiResponse> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): RandomApiResponse {
            val userApi = RandomApiResponse()
            val jsonObject = json!!.asJsonObject

            if (jsonObject.has("status")) {
                userApi.status = jsonObject.get("status").asString
            }
            if (jsonObject.has("message")) {
                userApi.image = jsonObject.get("message").asString
            }

            return userApi
        }

    }
}