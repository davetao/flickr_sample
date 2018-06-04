package com.davetao.flickrsample.repository.model

data class Photo(
        var id: String,
        var owner: String,
        var secret: String,
        var server: String,
        var farm: Int,
        var title: String,
        var isPublic: Boolean,
        var isFriend: Boolean,
        var isFamily: Boolean
) {
    fun url(): String = "http://farm$farm.static.flickr.com/$server/${id}_$secret.jpg"
}