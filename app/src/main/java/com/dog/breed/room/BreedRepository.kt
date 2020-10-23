package com.dog.breed.room

import androidx.lifecycle.LiveData

class BreedRepository(private val breedDao: BreedDao) {

    val readAllData: LiveData<List<Breed>> = breedDao.readAllData()

    suspend fun addBreed(breed: Breed){
        breedDao.addBreed(breed)
    }

    suspend fun deleteByBreed(breed: String){
        breedDao.deleteByBreed(breed)
    }
}