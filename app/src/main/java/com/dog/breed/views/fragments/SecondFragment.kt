package com.dog.breed.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.dog.breed.R
import com.dog.breed.viewModel.UserViewModel
import com.dog.breed.views.base.MyBaseFragment
import kotlinx.android.synthetic.main.second_fragment.*

class SecondFragment : MyBaseFragment() {
    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.second_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLoader(viewModel)
        initViews()
    }

    private fun initViews() {
        viewModel.getRandomImage()

        refreshBN.setOnClickListener {
            viewModel.getRandomImage()
        }
    }

    override fun onErrorCalled(it: String?) {
        showSnackbar(it!!)
    }

    override fun initObservers() {
        viewModel.randomImage.observe(this, Observer {
            randomIV.load(it)
        })
    }

    override fun onResume() {
        super.onResume()
    }

}