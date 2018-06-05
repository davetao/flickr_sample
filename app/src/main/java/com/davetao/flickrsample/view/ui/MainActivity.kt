package com.davetao.flickrsample.view.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import com.davetao.flickrsample.FlickrApplication
import com.davetao.flickrsample.R
import com.davetao.flickrsample.view.adapter.ImageAdapter
import com.davetao.flickrsample.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel
    private lateinit var searchViewItem: SearchView
    private lateinit var searchViewMenuItem: MenuItem
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as FlickrApplication).component.inject(this)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, factory)[MainViewModel::class.java]
        adapter = ImageAdapter()
        view_search_results.adapter = adapter

        val gridMargin = resources.getDimensionPixelOffset(R.dimen.margin)

        view_search_results.layoutManager = GridLayoutManager(baseContext, 3)
        view_search_results.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                outRect?.set(gridMargin, gridMargin, gridMargin, gridMargin)
            }
        })

        view_refresh_layout.isEnabled = false

        viewModel.searchState.observe(this, Observer { screenState ->
            view_refresh_layout.isRefreshing = screenState?.isSearching ?: false
            adapter.items = screenState?.searchResults ?: listOf()
            adapter.notifyDataSetChanged()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.search, menu)

        searchViewMenuItem = menu?.findItem(R.id.app_bar_search) ?: return false
        searchViewItem = searchViewMenuItem.actionView as SearchView
        searchViewItem.queryHint = getString(R.string.search_text_hint)
        searchViewItem.setOnCloseListener { viewModel.clearSearchTerm() }
        searchViewItem.setOnQueryTextListener(this)

        return true
    }

    // to repopulate the search view and redisplay it
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val query = viewModel.searchState.value?.searchTerm ?: ""
        if(query.isNotBlank()) {
            searchViewItem.isIconified = false // to expand the actionview
            searchViewItem.clearFocus()
            searchViewItem.setQuery(query, false)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(term: String?): Boolean {
        viewModel.applySearchTerm(term?.trim() ?: "")
        searchViewItem.clearFocus()
        return true
    }

    override fun onQueryTextChange(term: String?): Boolean {
        return false
    }

}
