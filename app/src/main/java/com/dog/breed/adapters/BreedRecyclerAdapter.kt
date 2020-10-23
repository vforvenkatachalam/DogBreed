package com.dog.breed.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dog.breed.R
import com.dog.breed.models.gson.BreedData
import java.util.*
import kotlin.collections.ArrayList

class BreedRecyclerAdapter(
    var context: Context?,
    var breedList: ArrayList<BreedData>,
    var mListener: BreedRecyclerAdapter.breedClickListener
) : RecyclerView.Adapter<BreedRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedRecyclerAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTV.setText(breedList.get(position).breedTitle?.capitalize(Locale.ROOT))
        holder.favCB.isChecked = breedList.get(position).breedFav
    }

    override fun getItemCount(): Int {
        return breedList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var titleTV:TextView = itemView.findViewById(R.id.itemBreedTitleTV)
        var favCB:CheckBox = itemView.findViewById(R.id.itemBreedHeartinCB)

        init {
            favCB.setOnClickListener {
                mListener.onFavClicked(adapterPosition)
            }
            itemView.setOnClickListener {
                mListener.onItemClicked(adapterPosition, it)
            }
        }
    }

    interface breedClickListener{
        fun onFavClicked(adapterPosition: Int)
        fun onItemClicked(adapterPosition: Int, view: View)
    }

}