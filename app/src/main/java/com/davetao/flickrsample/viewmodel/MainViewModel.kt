package com.davetao.flickrsample.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.davetao.flickrsample.repository.api.FlickrService
import com.davetao.flickrsample.viewmodel.model.ListImage
import com.davetao.flickrsample.viewmodel.model.SearchState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(var service: FlickrService) : ViewModel() {

    val searchState: MutableLiveData<SearchState> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()
    private var pageNumber = 0

    private val defaultSearchState = SearchState("", listOf(), false, false)

    init {
        searchState.postValue(defaultSearchState)
    }

    /**
     * Set the search term and request new data from the api.
     * Update the state of the view once finished
     */
    fun applySearchTerm(searchTerm: String) {

        val currentState = searchState.value ?: defaultSearchState

        currentState.isLoadingMore = false
        currentState.isSearching = true

        this.pageNumber = 0

        val disposable = service.search(searchTerm, pageNumber)
                .map { result ->
                    if (result.isOK()) {
                        result.photos.photo.map { photo ->
                            ListImage(
                                    photo.id,
                                    photo.url(),
                                    photo.title
                            )
                        }
                    } else {
                        throw Exception("Did not get the correct response from Flickr search results")
                    }
                }
                .doOnError {
                    // just show the localised error for the time being
                    currentState.searchError = it.localizedMessage
                }
                .doOnComplete {
                    currentState.isSearching = false
                    searchState.postValue(currentState)
                }
                .subscribe {
                    currentState.searchResults = it
                }

        compositeDisposable.add(disposable)
    }

//    fun loadMoreData() {
//        pageNumber++
//    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}