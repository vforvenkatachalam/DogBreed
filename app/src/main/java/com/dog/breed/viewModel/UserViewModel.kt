package com.dog.breed.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.dog.breed.enums.LoaderStatus
import com.dog.breed.models.gson.BreedData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UserViewModel(application: Application): MyBaseViewModel(application) {

    var dogBreeds: MutableLiveData<ArrayList<BreedData>> = MutableLiveData()
    var subBreeds: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var randomImage: MutableLiveData<String> = MutableLiveData()

    init {

    }
    fun getDogBreedsList() {
        isLoading.postValue(LoaderStatus.loading)

        CoroutineScope(exceptionHandler).launch {

            val request = retrofitManager.getUserApi().getBreeds()
            val response = request.await()

            if(isResponseSuccess(response)){
                val apiResponse = response.body()!!

                if(apiResponse.status.equals("success")){
                    apiResponse.message?.let {
                        dogBreeds.postValue(it)
                    }
                }else{
                    errorLiveData.postValue(/*Html.fromHtml(apiResponse.message).toString()*/"Something went wrong")
                }
            }
            isLoading.postValue(LoaderStatus.success)
        }
    }

    fun getBreedSubList(breed: String) {
        isLoading.postValue(LoaderStatus.loading)

        CoroutineScope(exceptionHandler).launch {

            val request = retrofitManager.getUserApi().getSubBreed(breed)
            val response = request.await()

            if(isResponseSuccess(response)){
                val apiResponse = response.body()!!

                if(apiResponse.status.equals("success")){
                    apiResponse.subBreedList.let {
                        subBreeds.postValue(it)
                    }
                }else{
                    errorLiveData.postValue(/*Html.fromHtml(apiResponse.message).toString()*/"Something went wrong")
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
                    apiResponse.image.let {
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