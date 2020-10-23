package com.dog.breed.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BreedViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Breed>>
    val readAllSubBreedData: LiveData<List<SubBreed>>
    val readSubBreedData: LiveData<List<SubBreed>>
    private val repository: BreedRepository

    init {
        val breedDao = BreedDatabase.getDatabase(application).breedDao()
        repository = BreedRepository(breedDao)
        readAllData = repository.readAllData
        readAllSubBreedData = repository.readAllSubBreedData
        readSubBreedData = repository.readBreedData
    }

    fun addBreed(breed: Breed){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBreed(breed)
        }
    }

    fun addSubBreed(subBreed: SubBreed){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSubBreed(subBreed)
        }
    }

    fun deleteByBreed(breed: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteByBreed(breed)
        }
    }

    fun deleteBySubBreed(subBreed: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBySubBreed(subBreed)
        }
    }

    fun readByBreed(breedName: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.readByBreed(breedName)
        }
    }
}