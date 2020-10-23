package com.dog.breed.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dog.breed.R
import com.dog.breed.adapters.BreedRecyclerAdapter
import com.dog.breed.models.gson.BreedData
import com.dog.breed.room.BreedViewModel
import kotlinx.android.synthetic.main.third_fragment.*

class ThirdFragment : Fragment(), BreedRecyclerAdapter.breedClickListener {
    private val breedViewModel: BreedViewModel by lazy {
        ViewModelProvider(this).get(BreedViewModel::class.java)
    }

    private lateinit var breedRecyclerAdapter: BreedRecyclerAdapter
    private val breedList: ArrayList<BreedData> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.third_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

        breedViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            var breedData:ArrayList<BreedData> = ArrayList()
            for(i in it){
                breedData.add(BreedData(i.name,i.favorite))
            }
            breedList.clear()
            breedList.addAll(breedData)
            breedRecyclerAdapter.notifyDataSetChanged()
        })

        breedRecyclerAdapter = BreedRecyclerAdapter(context, breedList, this)

        breedFromDBRV.layoutManager = LinearLayoutManager(activity)
        breedFromDBRV.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        breedFromDBRV.adapter = breedRecyclerAdapter
    }

    override fun onFavClicked(adapterPosition: Int) {
        breedViewModel.deleteByBreed(breedList.get(adapterPosition).breedTitle!!)
    }

    override fun onItemClicked(adapterPosition: Int, view: View) {
        val action = ThirdFragmentDirections.actionBnThirdToSubListFromFragment()
        view.findNavController().navigate(action)
    }
}