package com.dog.breed.managers.retrofit

import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ConfigApi {

    /*@GET("config")
    fun checkForceUpdate(@Query("android_version")username: String) : Deferred<Response<ForceUpdateApiResponse>>

    @PATCH("config")
    fun presignedUrl(@Body gsonObject: JsonObject): Deferred<Response<PreSignedApiResponse>>*/

    //upload file
    @PUT
    fun uploadFile(@Url url: String?, @Body body: RequestBody?): Deferred<Response<String>>
}