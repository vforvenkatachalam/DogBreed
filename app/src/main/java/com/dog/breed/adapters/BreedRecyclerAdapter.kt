package com.dog.breed.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dog.breed.R
import com.dog.breed.models.gson.BreedData

class BreedRecyclerAdapter(var context: Context, var breedList: ArrayList<BreedData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_breed, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return breedList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

}
