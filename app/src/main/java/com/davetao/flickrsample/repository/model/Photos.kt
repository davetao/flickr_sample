package com.davetao.flickrsample.repository.model

data class Photos(
        var page: Int,
        var pages: Int,
        var perpage: Int,
        var total: Int,
        var photo: List<Photo>
)