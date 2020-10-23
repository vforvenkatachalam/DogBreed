package com.dog.breed.models.gson

class BreedData (
    var breedTitle : String ?= null,
    var hasSubList : Boolean = false,
    var breedFav : Boolean = false,
    val subList : ArrayList<String> = ArrayList()
)
