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

    @Query("DELETE FROM breed_table WHERE name = :breed")
    fun deleteByBreed(breed:String)

}