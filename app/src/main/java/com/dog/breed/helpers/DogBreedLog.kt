package com.dog.breed.helpers

import android.util.Log
import com.dog.breed.DogBreedApp

object DogBreedLog {

    fun e(TAG: String, MESSAGE: String) {
        if (DogBreedApp.DEBUG)
            Log.e(TAG, MESSAGE)
    }

    fun d(TAG: String, MESSAGE: String) {
        if (DogBreedApp.DEBUG)
            Log.d(TAG, MESSAGE)
    }

    fun i(TAG: String, MESSAGE: String) {
        if (DogBreedApp.DEBUG)
            Log.i(TAG, MESSAGE)
    }

    fun v(TAG: String, MESSAGE: String) {
        if (DogBreedApp.DEBUG)
            Log.v(TAG, MESSAGE)
    }

    fun w(TAG: String, MESSAGE: String) {
        if (DogBreedApp.DEBUG)
            Log.w(TAG, MESSAGE)
    }
}