package com.dog.breed.models

import com.google.gson.Gson
import org.json.JSONObject

class ErrorResponse(jsonString:String){
    var errorCode: String? = null
    var status : Int ?= null
    var data : ErrorData ?= null

    init {
        val jsonObject = JSONObject(jsonString)

        if(jsonObject.has("code"))
            errorCode = jsonObject.getString("code")
        if(jsonObject.has("status"))
            status = jsonObject.getInt("status")
        if(jsonObject.has("data"))
            data = Gson().fromJson(jsonObject.getString("data"),ErrorData::class.java)
    }
}