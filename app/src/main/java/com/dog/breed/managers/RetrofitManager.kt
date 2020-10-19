package com.dog.breed.managers

import android.content.Context
import android.content.ContextWrapper
import com.dog.breed.DogBreedApp
import com.dog.breed.R
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.dog.breed.constants.DogBreedEnv
import com.dog.breed.managers.retrofit.*
import com.dog.breed.managers.utils.ConnectivityInterceptor
import com.dog.breed.models.response.BreedsApiResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

class RetrofitManager(context: Context) : ContextWrapper(context) {

    companion object {
        private var INSTANCE: RetrofitManager? = null
        private lateinit var defaultRetrofit: Retrofit
        private var userRetrofit: Retrofit? = null
        private var businessUserRetrofit: Retrofit? = null
        private var fileUploadRetrofit: Retrofit? = null


        fun getInstance(context: Context): RetrofitManager {
            if (INSTANCE == null) {
                initManager(context)
            }
            return INSTANCE!!
        }

        private fun initManager(context: Context) {
            INSTANCE = RetrofitManager(context)

            val interceptor = Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Accept", "*/*")
                    .header("Content-Type" ,"application/json")
                    .build()

                chain.proceed(request)
            }

            val httpClient: OkHttpClient.Builder =
                OkHttpClient.Builder().addInterceptor(interceptor)
                    .addInterceptor(ConnectivityInterceptor(context))
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(200, TimeUnit.SECONDS);

            if (!DogBreedEnv.PROD_MODE.equals(context.getString(R.string.environment))) {
                val logInterceptor = HttpLoggingInterceptor()
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(logInterceptor)
            }


            val gson = GsonBuilder()
                .registerTypeAdapter(BreedsApiResponse::class.java, BreedsApiResponse.BreedsApiDeserializer())
               .create()



            defaultRetrofit = Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))// + context.getString(R.string.url_version))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(httpClient.build())
                .build()

        }
    }

    private fun getFileUploadRetrofit(): Retrofit? {
        if (fileUploadRetrofit == null) {
            createFileUploadRetrofit()
        }
        return fileUploadRetrofit
    }


    private fun createFileUploadRetrofit() {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(ConnectivityInterceptor(applicationContext))
        httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Content-Type", "image/jpeg")

                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        })
        if (!DogBreedEnv.PROD_MODE.equals(baseContext.getString(R.string.environment))) {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(logInterceptor)
        }
        val client = httpClient
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
        //        userRetrofit = new Retrofit.Builder().baseUrl(getBaseContext().getString(R.string.base_url) + getBaseContext().getString(R.string.api_version))
        fileUploadRetrofit = Retrofit.Builder().baseUrl(getString(R.string.base_url))
            .addConverterFactory(NullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient.build())
            .build()
    }

    val NullOnEmptyConverterFactory = object : Converter.Factory() {
        fun converterFactory() = this
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ) = object : Converter<ResponseBody, Any?> {
            val nextResponseBodyConverter =
                retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)

            override fun convert(value: ResponseBody) =
                if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
        }
    }

    private fun getUserRetrofit(context: Context): Retrofit {

        if (userRetrofit == null) {
            val interceptor = Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Accept", "*/*")
                    .header("Content-Type" ,"application/json")
                    .header("Authorization" , "Bearer " +DogBreedApp.getAuthToken(context))
                    .build()

                chain.proceed(request)
            }

            val gson = GsonBuilder()
                    //user
                /*.registerTypeAdapter(UpdateProfileApiResponse::class.java, UpdateProfileApiResponse.UpdateProfileApiDeserializer())
                .registerTypeAdapter(ChangePasswordApiResponse::class.java, ChangePasswordApiResponse.ChangePasswordApiDeserializer())
                .registerTypeAdapter(SearchStoreApiResponse::class.java, SearchStoreApiResponse.SearchStoreApiDeserializer())
                .registerTypeAdapter(StoreApiResponse::class.java, StoreApiResponse.StoreApiDeserializer())
                .registerTypeAdapter(ScannedApiResponse::class.java, ScannedApiResponse.ScannedStoreApiDeserializer())
                .registerTypeAdapter(OffersApiResponse::class.java, OffersApiResponse.OffersApiDeserializer())
                .registerTypeAdapter(MessageApiResponse::class.java, MessageApiResponse.MessageApiDeserializer())
                .registerTypeAdapter(IndivMessageApiResponse::class.java, IndivMessageApiResponse.IndivMessageApiDeserializer())
                .registerTypeAdapter(HistoryApiResponse::class.java, HistoryApiResponse.HistoryApiDeserializer())
                .registerTypeAdapter(UnfollowStoreApiResponse::class.java, UnfollowStoreApiResponse.UnfollowApiDeserializer())
                .registerTypeAdapter(LogoutApiResponse::class.java, LogoutApiResponse.LogoutApiDeserializer())
                .registerTypeAdapter(PurchaseOfferApiResponse::class.java, PurchaseOfferApiResponse.PurchaseOfferApiDeserializer())
                .registerTypeAdapter(OfferApiResponse::class.java, OfferApiResponse.OfferApiDeserializer())
                .registerTypeAdapter(SocialMediaLinksApiResponse::class.java, SocialMediaLinksApiResponse.SocialMediaLinksApiDeserializer())*/
               .create()


            val httpClient: OkHttpClient.Builder =
                OkHttpClient.Builder().addInterceptor(interceptor)
                    .addInterceptor(ConnectivityInterceptor(baseContext))
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(200, TimeUnit.SECONDS);

            if (!DogBreedEnv.PROD_MODE.equals(baseContext.getString(R.string.environment))) {
                val logInterceptor = HttpLoggingInterceptor()
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(logInterceptor)
            }

            userRetrofit = Retrofit.Builder()
                .baseUrl(baseContext.getString(R.string.base_url)) //+ context.getString(R.string.auth_v1_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(httpClient.build())
                .build()
        }

        return userRetrofit!!
    }


    /*fun getToken() : String ? {
        return SharedPrefManager.getInstance(baseContext).getPreference(StackedConstants.AUTH_TOKEN);
    }*/


    /*fun getConfigApi () : ConfigApi {
        return defaultRetrofit.create(ConfigApi::class.java)
    }

    fun getLaunchApi() : LaunchApi {
        return defaultRetrofit.create(LaunchApi::class.java)
    }

    fun getUserApi() : UserApi {
        return getUserRetrofit(baseContext).create(UserApi::class.java)
    }

    fun getBusinessLaunchApi() :  BusinessLaunchApi {
        return defaultRetrofit.create(BusinessLaunchApi::class.java)
    }

    fun getBusinessUserApi() :  BusinessUserApi {
        return getBusinessUserRetrofit(baseContext).create(BusinessUserApi::class.java)
    }

    fun getUploadFileApi(): ConfigApi {
        return getFileUploadRetrofit()!!.create(ConfigApi::class.java)
    }

    fun getNotificationApi(): NotificationApi {
        return getUserRetrofit(baseContext).create(NotificationApi::class.java)
    }*/

//
//    fun getPrivacyPolicyApi() : SettingsAPI {
//        return getUserRetrofit(baseContext).create(SettingsAPI::class.java)
//    }

    fun clearAll() {
        INSTANCE = null
        userRetrofit == null
    }

    fun logout() {
        userRetrofit = null
    }

    fun getUserApi(): UserApi {
        return defaultRetrofit.create(UserApi::class.java)
    }

}