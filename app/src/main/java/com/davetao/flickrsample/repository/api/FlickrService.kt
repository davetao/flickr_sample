package com.davetao.flickrsample.repository.api

import com.davetao.flickrsample.repository.model.SearchResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface FlickrService {

    @GET("method=flickr.photos.search&format=json&nojsoncallback=1&text={searchTerm}&page={page}")
    fun search(
            @Path("searchTerm") searchTerm: String,
            @Path("page") page: Int
    ): Observable<SearchResult>

}