package com.dog.breed.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Breed::class], version = 1, exportSchema = false)
abstract class BreedDatabase: RoomDatabase() {

    abstract fun breedDao(): BreedDao

    companion object {
        @Volatile
        private var INSTANCE: BreedDatabase? = null

        fun getDatabase(context: Context): BreedDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BreedDatabase::class.java,
                    "breed_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}