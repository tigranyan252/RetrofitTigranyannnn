package com.example.retrofittigranyan
import com.example.retrofittigranyan.data.model.PostalCodeInfo
import retrofit2.Response // Для использования с корутинами
import retrofit2.http.GET
import retrofit2.http.Path

interface ZippopotamApiService {

    // Пример эндпоинта: /us/90210
    // {country} и {postalCode} будут заменены значениями, переданными в функцию
    @GET("/{country}/{postalCode}")
    suspend fun getPostalCodeInfo(
        @Path("country") countryCode: String,
        @Path("postalCode") postalCode: String
    ): Response<PostalCodeInfo> // Используем Response<T> для получения полного ответа, включая статус код

    // Альтернатива без корутин (использует Call<T> и колбэки)
    // @GET("/{country}/{postalCode}")
    // fun getPostalCodeInfoCallback(
    //     @Path("country") countryCode: String,
    //     @Path("postalCode") postalCode: String
    // ): Call<PostalCodeInfo>
}