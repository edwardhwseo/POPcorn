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
    val rating: Double = 0.0,
    @SerializedName("year")
    val year: String
) {
    constructor() : this(
        "", "", "", "", "", "", 0.0, "" // Initialize default values
    )
}