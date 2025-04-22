package com.example.retrofittigranyan.data.model
import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("place name")
    val placeName: String,
    val longitude: String,
    val state: String,
    @SerializedName("state abbreviation")
    val stateAbbreviation: String,
    val latitude: String
)