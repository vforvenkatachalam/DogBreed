package com.dog.breed.managers.retrofit

import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LaunchApi {

    /*@POST("auth/signup")
    fun signUp(@Body gsonObject : JsonObject) : Deferred<Response<SignUpApiResponse>>

    @PUT("auth/verify/{id}")
    fun verifyNumber(@Body gsonObject: JsonObject, @Path("id") id: String) : Deferred<Response<OTPVerifyApiResponse>>

    @POST("auth/login")
    fun login(@Body gsonObject : JsonObject) : Deferred<Response<SignUpApiResponse>>

    @POST("auth/forgotpassword")
    fun forgotPassword(@Body gsonObject : JsonObject) : Deferred<Response<ForgotPasswordApiResponse>>

    @PUT("auth/resetpassword")
    fun resetPassword(@Body gsonObject : JsonObject) : Deferred<Response<ForgotPasswordApiResponse>>

    @PUT("auth/resend/{id}")
    fun resendVerification(@Path("id") id: String): Deferred<Response<ResendVerificationApiResponse>>*/
}