package com.dog.breed.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dog.breed.R
import com.dog.breed.adapters.BreedRecyclerAdapter
import com.dog.breed.models.gson.BreedData
import com.dog.breed.viewModel.UserViewModel
import com.dog.breed.views.base.MyBaseFragment
import kotlinx.android.synthetic.main.first_fragment.*

class FirstFragment : MyBaseFragment() {
    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }
    private val breedList: ArrayList<BreedData> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLoader(viewModel)
        initViews()
    }

    private fun initViews() {
        viewModel.getDogBreedsList()

        /*var breedRecyclerAdapter = BreedRecyclerAdapter(this, breedList)

        breedRV.layoutManager = GridLayoutManager(activity, 2)
        breedRV.adapter = breedRecyclerAdapter*/
    }

    override fun onErrorCalled(it: String?) {
        showSnackbar(it!!)
    }

    override fun initObservers() {
        viewModel.dogBreeds.observe(this, Observer {
            showSnackbar("Data Received")
        })
    }
}