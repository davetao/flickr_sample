package com.davetao.flickrsample.viewmodel.model

data class SearchState(
        var searchTerm: String,
        var searchResults: List<ListImage>,
        var isSearching: Boolean,
        var isLoadingMore: Boolean,
        var searchError: String = ""
)