package com.example.retrofittigranyan.data.model
import com.google.gson.annotations.SerializedName

data class PostalCodeInfo(
    @SerializedName("post code")
    val postCode: String,
    val country: String,
    @SerializedName("country abbreviation")
    val countryAbbreviation: String,
    val places: List<Place> // Ссылка на другой data class
)