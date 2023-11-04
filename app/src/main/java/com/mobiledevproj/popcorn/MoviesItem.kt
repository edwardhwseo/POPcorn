package com.mobiledevproj.popcorn
import com.google.gson.annotations.SerializedName

data class MoviesItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("images")
    val images: String,
    @SerializedName("rating")
    val rating: Number,
    @SerializedName("year")
    val year: String
)

