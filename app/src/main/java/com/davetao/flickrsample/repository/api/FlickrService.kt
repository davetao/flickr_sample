package com.davetao.flickrsample.repository.api

import com.davetao.flickrsample.repository.model.SearchResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1")
    fun search(
            @Query("text") searchTerm: String,
            @Query("page") page: Int
    ): Observable<SearchResult>

}