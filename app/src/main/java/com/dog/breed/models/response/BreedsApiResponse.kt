package com.dog.breed.models.response

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type


class BreedsApiResponse : BaseApiResponse(){

    class BreedsApiDeserializer : JsonDeserializer<BreedsApiResponse> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): BreedsApiResponse {
            val userApi = BreedsApiResponse()
            val jsonObject = json!!.asJsonObject

            if (jsonObject.has("status")) {
                userApi.status = jsonObject.get("status").asString
            }
            if (jsonObject.has("message")) {
                //userApi.message = "Data"
                var messageObj: JsonObject? = jsonObject.get("message").asJsonObject
                var temp:ArrayList<String> = ArrayList()
                for(x in messageObj!!.keySet()){
                    temp.add(x.length.toString())
                }

                //userApi.message = jsonObject.get("message").asJsonObject
            }

            return userApi
        }

    }

}
