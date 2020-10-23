package com.dog.breed.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BreedViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Breed>>
    private val repository: BreedRepository

    init {
        val breedDao = BreedDatabase.getDatabase(application).breedDao()
        repository = BreedRepository(breedDao)
        readAllData = repository.readAllData
    }

    fun addBreed(breed: Breed){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBreed(breed)
        }
    }

    fun deleteByBreed(breed: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteByBreed(breed)
        }
    }

}