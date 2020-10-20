package com.dog.breed.viewModel

import android.app.Application
import android.text.Html
import androidx.lifecycle.MutableLiveData
import com.dog.breed.enums.LoaderStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UserViewModel(application: Application): MyBaseViewModel(application) {

    var dogBreeds: MutableLiveData<String> = MutableLiveData()
    var randomImage: MutableLiveData<String> = MutableLiveData()

    fun getDogBreedsList() {
        isLoading.postValue(LoaderStatus.loading)

        CoroutineScope(exceptionHandler).launch {

            val request = retrofitManager.getUserApi().getBreeds()
            val response = request.await()

            if(isResponseSuccess(response)){
                val apiResponse = response.body()!!

                if(apiResponse.status.equals("success")!!){
                    /*apiResponse.message?.let {
                        //dogBreeds.postValue(it)
                    }*/
                }else{
                    //errorLiveData.postValue(Html.fromHtml(apiResponse.message).toString())
                }
            }
            isLoading.postValue(LoaderStatus.success)
        }
    }

    fun getRandomImage() {
        isLoading.postValue(LoaderStatus.loading)

        CoroutineScope(exceptionHandler).launch {

            val request = retrofitManager.getUserApi().getRandomImg()
            val response = request.await()

            if(isResponseSuccess(response)){
                val apiResponse = response.body()!!

                if(apiResponse.status.equals("success")){
                    apiResponse.message.let {
                        randomImage.postValue(it)
                    }
                }else{
                    errorLiveData.postValue("Something went wrong")
                }
            }
            isLoading.postValue(LoaderStatus.success)
        }
    }

}