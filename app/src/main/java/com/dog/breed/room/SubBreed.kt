package com.dog.breed.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub_breed_table")
data class SubBreed (
    @PrimaryKey()
    val subBreedName:String,
    val breedName:String,
    val hasSubList:Boolean,
    val favorite:Boolean
)