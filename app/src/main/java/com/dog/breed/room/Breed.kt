package com.dog.breed.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed_table")
data class Breed (
    @PrimaryKey()
    val name:String,
    val hasSubList:Boolean,
    var favorite:Boolean
)