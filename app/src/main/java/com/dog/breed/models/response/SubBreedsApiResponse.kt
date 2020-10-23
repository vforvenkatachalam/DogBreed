package com.dog.breed.models.response

import com.dog.breed.utils.JsonUtils
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.json.JSONObject
import java.lang.reflect.Type

class SubBreedsApiResponse: BaseApiResponse() {
    var subBreedList: ArrayList<String> = ArrayList()

    class SubBreedsApiDeserializer : JsonDeserializer<SubBreedsApiResponse> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): SubBreedsApiResponse {
            val userApi = SubBreedsApiResponse()
            val jsonObject = json!!.asJsonObject

            if (jsonObject.has("status")) {
                userApi.status = jsonObject.get("status").asString
            }
            if (jsonObject.has("message")) {

                //val resObj = JSONObject(JsonUtils.toJson(jsonObject.get("message").asJsonArray))
                val subBreeds = jsonObject.get("message").asJsonArray

                for(i in subBreeds) {
                    userApi.subBreedList.add(i.asString)
                }

                /*val keys = resObj.keys()
                val breedList: ArrayList<BreedData> = ArrayList()

                while (keys.hasNext()){
                    val key = keys.next() as String
                    val jsonArr: JSONArray = resObj.get(key) as JSONArray
                    //DogBreedLog.d("KEY", temp.length().toString())

                    breedList.add(BreedData(key, false, jsonArr.length()>0))
                }
                userApi.message?.addAll(breedList)*/
            }

            return userApi
        }

    }
}
