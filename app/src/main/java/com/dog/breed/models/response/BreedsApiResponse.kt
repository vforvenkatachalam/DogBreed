package com.dog.breed.models.response

import com.dog.breed.helpers.DogBreedLog
import com.dog.breed.models.gson.BreedData
import com.dog.breed.utils.JsonUtils
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type
import kotlin.collections.ArrayList


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

                val resObj = JSONObject(JsonUtils.toJson(jsonObject.get("message").asJsonObject))

                val keys = resObj.keys()
                val breedList: ArrayList<BreedData> = ArrayList()

                while (keys.hasNext()){
                    val key = keys.next() as String
                    val jsonArr:JSONArray = resObj.get(key) as JSONArray
                    //DogBreedLog.d("KEY", temp.length().toString())
                    var subList: ArrayList<String> = ArrayList()
                    for(i in 0 until jsonArr.length()){
                        subList.add(jsonArr.get(i) as String)
                        //DogBreedLog.d("KEY", jsonArr.get(i) as String)
                    }

                    breedList.add(BreedData(key, subList.size>0, false, subList))
                }
                userApi.message?.addAll(breedList)
            }

            return userApi
        }

    }

}
