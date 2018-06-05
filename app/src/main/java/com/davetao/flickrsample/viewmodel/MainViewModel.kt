package com.davetao.flickrsample.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.davetao.flickrsample.repository.api.FlickrService
import com.davetao.flickrsample.viewmodel.model.ListImage
import com.davetao.flickrsample.viewmodel.model.SearchState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private var service: FlickrService) : ViewModel() {

    val searchState: MutableLiveData<SearchState> = MutableLiveData()

    private var activeSearchTask: Disposable? = null
    private var pageNumber = 0
    val PAGE_SIZE: Int = 100

    /**
     * Set the search term and request new data from the api.
     * Update the state of the view once finished
     */
    fun applySearchTerm(searchTerm: String) {

        val currentState = searchState.value ?: SearchState()
        currentState.isLoadingMore = false
        currentState.searchTerm = searchTerm

        this.pageNumber = 1
        if(searchTerm.isBlank()) {
            currentState.searchResults = mutableListOf()
            searchState.postValue(currentState)
        }
        else {

            activeSearchTask = applySearch(currentState)
                    .subscribe {
                        currentState.searchResults.clear()
                        currentState.searchResults.addAll(it)
                    }

        }
    }

    fun loadMore() {

        val currentState = searchState.value ?: SearchState()
        currentState.isLoadingMore = true
        pageNumber++

        activeSearchTask = applySearch(currentState)
                .subscribe { currentState.searchResults.addAll(it) }

    }


    private fun applySearch(currentState: SearchState) =
            service.search(currentState.searchTerm, pageNumber, PAGE_SIZE)
                .doOnSubscribe {
                    currentState.isSearching = true
                    searchState.postValue(currentState)
                }
                .subscribeOn(Schedulers.io())
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

    override fun onCleared() {
        super.onCleared()
        activeSearchTask?.dispose()
    }

    fun clearSearchTerm(): Boolean {
        activeSearchTask?.dispose()
        searchState.postValue(SearchState())
        return false
    }
}