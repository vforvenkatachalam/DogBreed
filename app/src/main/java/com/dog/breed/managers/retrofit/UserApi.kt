package com.dog.breed.managers.retrofit

import com.dog.breed.models.response.BreedsApiResponse
import com.dog.breed.models.response.RandomApiResponse
import com.dog.breed.models.response.SubBreedsApiResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @GET("breeds/list/all")
    fun getBreeds() : Deferred<Response<BreedsApiResponse>>

    @GET("breed/{breed}/list")
    fun getSubBreed(@Path("breed") breed:String) : Deferred<Response<SubBreedsApiResponse>>

    @GET("breeds/image/random")
    fun getRandomImg() : Deferred<Response<RandomApiResponse>>
}