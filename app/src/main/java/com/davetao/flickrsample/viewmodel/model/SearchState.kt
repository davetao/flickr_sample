package com.davetao.flickrsample.viewmodel.model

data class SearchState(
        var searchTerm: String = "",
        var searchResults: MutableList<ListImage> = mutableListOf(),
        var isSearching: Boolean = false,
        var isLoadingMore: Boolean = false,
        var searchError: String = ""
)