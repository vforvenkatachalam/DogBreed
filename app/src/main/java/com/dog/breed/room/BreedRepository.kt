package com.dog.breed.room

import androidx.lifecycle.LiveData

class BreedRepository(private val breedDao: BreedDao) {

    var breedName: String = "hound"
    val readAllData: LiveData<List<Breed>> = breedDao.readAllData()
    val readAllSubBreedData: LiveData<List<SubBreed>> = breedDao.readAllSubBreedData()
    val readBreedData: LiveData<List<SubBreed>> = breedDao.readSubBreedData(breedName)

    suspend fun addBreed(breed: Breed){
        breedDao.addBreed(breed)
    }

    suspend fun addSubBreed(subBreed: SubBreed){
        breedDao.addSubBreed(subBreed)
    }

    suspend fun deleteByBreed(breed: String){
        breedDao.deleteByBreed(breed)
    }

    suspend fun deleteBySubBreed(subBreed: String){
        breedDao.deleteBySubBreed(subBreed)
    }

    suspend fun readByBreed(breed: String){
        breedName = breed
        /*readBreedData = breedDao.readSubBreedData(breed)*/
    }
}