package com.boris.movienotesmvvm.data.storage.remote.response

import com.google.gson.annotations.SerializedName

data class MoviesRemoteResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieResponse>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)