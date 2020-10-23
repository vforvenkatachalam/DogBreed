package com.dog.breed.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BreedDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBreed(breed: Breed)

    @Query("SELECT * FROM breed_table ORDER BY name ASC")
    fun readAllData(): LiveData<List<Breed>>

    @Query("SELECT * FROM sub_breed_table ORDER BY breedName ASC")
    fun readAllSubBreedData(): LiveData<List<SubBreed>>

    @Query("SELECT * FROM sub_breed_table WHERE breedName = :breedName")
    fun readSubBreedData(breedName: String): LiveData<List<SubBreed>>

    @Query("DELETE FROM breed_table WHERE name = :breed")
    fun deleteByBreed(breed:String)

    @Query("DELETE FROM sub_breed_table WHERE subBreedName = :subBreed")
    fun deleteBySubBreed(subBreed:String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSubBreed(subBreed: SubBreed)
}