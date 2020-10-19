package com.dog.breed.models

import com.google.gson.annotations.SerializedName

class ErrorData {
    @SerializedName("message")
    var message : String ?= null
}