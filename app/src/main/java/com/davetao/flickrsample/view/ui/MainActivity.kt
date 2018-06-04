package com.davetao.flickrsample.view.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.davetao.flickrsample.FlickrApplication
import com.davetao.flickrsample.R
import com.davetao.flickrsample.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as FlickrApplication).component.inject(this)
        viewModel = ViewModelProviders.of(this, factory)[MainViewModel::class.java]
        setContentView(R.layout.activity_main)

        viewModel.searchState.observe(this, Observer { screenState ->

            // set the state of the view
        })
    }
}
