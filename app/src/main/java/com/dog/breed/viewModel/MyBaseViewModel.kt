package com.dog.breed.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.dog.breed.DogBreedApp
import com.dog.breed.R
import com.dog.breed.models.ErrorResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.dog.breed.enums.LoaderStatus
import com.dog.breed.managers.RetrofitManager
import com.dog.breed.managers.SharedPrefManager
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.Response
import java.util.*
import kotlin.coroutines.CoroutineContext


open class MyBaseViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + rootJob

    protected val TAG: String = this.javaClass.simpleName

    protected var errorLiveData = MutableLiveData<String?>()

    // ...because this is what we'll want to expose
    val errorMediatorLiveData = MediatorLiveData<String?>()

    var isLoading = MutableLiveData<LoaderStatus>()

    protected val retrofitManager: RetrofitManager by lazy { RetrofitManager.getInstance(getApplication()) }

    val rootJob = Job()


    init {
        errorMediatorLiveData.addSource(errorLiveData) { result: String? ->
            result?.let {
                errorMediatorLiveData.value = result
            }
        }
    }

    val sharedPrefManager: SharedPrefManager
        get() {
            return SharedPrefManager.getInstance(getApplication())
        }

    protected val exceptionHandler: CoroutineContext =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            isLoading.postValue(LoaderStatus.failed)
            errorLiveData.postValue(throwable.message)
            throwable.printStackTrace()
        }


    //Remote config
    /*protected val remoteConfig: FirebaseRemoteConfig by lazy {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(
                getApplication<StackedApp>().resources.getInteger(
                    R.integer.REMOTE_CONFIG_INTERVAL_SECONDS
                ).toLong()
            )
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        return@lazy remoteConfig
    }*/


    protected fun getGsonObject(obj: JSONObject): JsonObject {
        val jsonParser = JsonParser()
        return jsonParser.parse(obj.toString()) as JsonObject
    }

    protected fun <T : Any> isResponseSuccess(response: Response<T>): Boolean {
        if (!response.isSuccessful) {
            isLoading.postValue(LoaderStatus.failed)
            if (response.errorBody() != null) {
                val jsonString = response.errorBody()!!.string()
                if (jsonString.contains("{")) {
                    val errorModel = ErrorResponse(jsonString)
                    errorLiveData.postValue(errorModel.data?.message)
                } else {
                    errorLiveData.postValue(response.message())
                }
            } else if (!response.message().isEmpty())
                errorLiveData.postValue(response.message())
            else
                errorLiveData.postValue(getApplication<DogBreedApp>().getString(R.string.something_wrong))
        }
        return response.isSuccessful
    }


    override fun onCleared() {
        super.onCleared()
        rootJob.cancel()
    }
}