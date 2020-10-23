package com.dog.breed.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dog.breed.R
import com.dog.breed.adapters.BreedRecyclerAdapter
import com.dog.breed.helpers.DogBreedLog
import com.dog.breed.models.gson.BreedData
import com.dog.breed.room.BreedViewModel
import com.dog.breed.room.SubBreed
import com.dog.breed.utils.JsonUtils
import com.dog.breed.viewModel.UserViewModel
import com.dog.breed.views.base.MyBaseFragment
import kotlinx.android.synthetic.main.fragment_sublist.*

class SubListFragment: MyBaseFragment(), BreedRecyclerAdapter.breedClickListener {
    private val breedViewModel: BreedViewModel by lazy {
        ViewModelProvider(this).get(BreedViewModel::class.java)
    }
    private lateinit var breedRecyclerAdapter: BreedRecyclerAdapter
    private val breedList: ArrayList<BreedData> = ArrayList()
    /*private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }*/
    private val args by navArgs<SubListFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_sublist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setUpLoader(viewModel)
        initViews()
    }

    private fun initViews() {
        //viewModel.getBreedSubList(args.breedName)

        var list = JsonUtils.parseJsonToList<String>(args.breedList)
        //DogBreedLog.d("TEST", JsonUtils.toJson(list))
        for(i in list){
            breedList.add(BreedData(i,))
        }
        breedRecyclerAdapter = BreedRecyclerAdapter(context, breedList, this)
        breedSubListRV.layoutManager = LinearLayoutManager(activity)
        breedSubListRV.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        breedSubListRV.adapter = breedRecyclerAdapter
    }

    override fun initObservers() {
        /*viewModel.subBreeds.observe(this, Observer {
            //showSnackbar(JsonUtils.toJson(it))
            if(it.size>0) {
                breedList.clear()
                for (i in it) {
                    breedList.add(BreedData(i,false))
                }
            }
            breedRecyclerAdapter.notifyDataSetChanged()
        })*/
    }

    override fun onErrorCalled(it: String?) {
        showSnackbar(it!!)
    }

    override fun onFavClicked(adapterPosition: Int) {
        if(breedList.get(adapterPosition).breedFav){
            showSnackbar("Marked as UnFavorite")
        }else {
            /*breedViewModel.addSubBreed(SubBreed(
                breedList.get(adapterPosition).breedTitle!!,
                breedList.get(adapterPosition).breedTitle!!,
            false,
            false))
            showSnackbar("Added to Favorites.!")*/
        }
    }

    override fun onItemClicked(adapterPosition: Int, view: View) {

    }
}