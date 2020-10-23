package com.dog.breed.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.dog.breed.R
import com.dog.breed.adapters.BreedRecyclerAdapter
import com.dog.breed.models.gson.BreedData
import com.dog.breed.room.BreedViewModel
import com.dog.breed.utils.JsonUtils
import com.dog.breed.views.base.MyBaseFragment

class SubListFromDBFragment : MyBaseFragment(), BreedRecyclerAdapter.breedClickListener {
    private val breedViewModel: BreedViewModel by lazy {
        ViewModelProvider(this).get(BreedViewModel::class.java)
    }
    private val args by navArgs<SubListFromDBFragmentArgs>()

    private lateinit var breedRecyclerAdapter: BreedRecyclerAdapter
    private val breedList: ArrayList<BreedData> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_list_from_db, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        showSnackbar(args.breedName)

        /*breedViewModel.readAllData

        breedRecyclerAdapter = BreedRecyclerAdapter(context, breedList, this)
        breedSubListFromDBRV.layoutManager = LinearLayoutManager(activity)
        breedSubListFromDBRV.adapter = breedRecyclerAdapter*/
    }

    override fun onFavClicked(adapterPosition: Int) {

    }

    override fun onItemClicked(adapterPosition: Int, view: View) {

    }

    override fun onErrorCalled(it: String?) {

    }

    override fun initObservers() {

    }

}