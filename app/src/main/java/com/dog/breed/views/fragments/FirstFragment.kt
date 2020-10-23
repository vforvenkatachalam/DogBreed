package com.dog.breed.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dog.breed.R
import com.dog.breed.adapters.BreedRecyclerAdapter
import com.dog.breed.models.gson.BreedData
import com.dog.breed.room.Breed
import com.dog.breed.room.BreedViewModel
import com.dog.breed.utils.JsonUtils
import com.dog.breed.viewModel.UserViewModel
import com.dog.breed.views.base.MyBaseFragment
import kotlinx.android.synthetic.main.first_fragment.*

class FirstFragment : MyBaseFragment(), BreedRecyclerAdapter.breedClickListener {
    private lateinit var breedRecyclerAdapter: BreedRecyclerAdapter
    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }
    private val breedViewModel: BreedViewModel by lazy {
        ViewModelProvider(this).get(BreedViewModel::class.java)
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

        breedRecyclerAdapter = BreedRecyclerAdapter(context, breedList, this)

        breedRV.layoutManager = LinearLayoutManager(activity)
        breedRV.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        breedRV.adapter = breedRecyclerAdapter
    }

    override fun onErrorCalled(it: String?) {
        showSnackbar(it!!)
    }

    override fun initObservers() {
        viewModel.dogBreeds.observe(this, Observer {
            breedList.addAll(it)
            breedViewModel.readAllData.observe(viewLifecycleOwner, Observer {
                for(temp in breedList){
                    for(i in it){
                        if(i.name.equals(temp.breedTitle)) {
                            temp.breedFav = i.favorite
                        }
                    }
                }
                breedRecyclerAdapter.notifyDataSetChanged()
            })
            breedRecyclerAdapter.notifyDataSetChanged()
        })
    }

    override fun onFavClicked(adapterPosition: Int) {
        //showSnackbar(breedList.get(adapterPosition).breedTitle!!)
        if(breedList.get(adapterPosition).breedFav){
            breedList.get(adapterPosition).breedFav = false
            breedRecyclerAdapter.notifyDataSetChanged()
        }else {
            breedList.get(adapterPosition).breedFav = true
            breedRecyclerAdapter.notifyDataSetChanged()
            breedViewModel.addBreed(Breed(
                breedList.get(adapterPosition).breedTitle!!,
                breedList.get(adapterPosition).subList.size>0,
                true))
            showSnackbar("Added to Favorites..!")
        }
    }

    override fun onItemClicked(adapterPosition: Int, view: View) {
        if(breedList.get(adapterPosition).subList.size>0){
            val action = FirstFragmentDirections.actionBnFirstToSubListFragment(JsonUtils.toJson(breedList.get(adapterPosition).subList),
                breedList.get(adapterPosition).breedTitle!!
            )
            view.findNavController().navigate(action)
        } else {
            showSnackbar("No Items Available in this Breed.!")
        }
    }
}