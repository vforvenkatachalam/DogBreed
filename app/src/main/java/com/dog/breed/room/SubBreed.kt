package com.dog.breed.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub_breed_table")
data class SubBreed (
    @PrimaryKey()
    val name:String,
    val subBreedName:String,
    val hasSubList:Boolean,
    val favorite:Boolean
)