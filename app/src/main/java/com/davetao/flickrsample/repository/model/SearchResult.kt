package com.davetao.flickrsample.repository.model

data class SearchResult(
        var photos: Photos,
        var stat: String
) {
    fun isOK(): Boolean = stat == "ok"
}