package com.example.retrofittigranyan
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor // Для логирования
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.zippopotam.us/"

    // Опционально: Настройка OkHttpClient для добавления логгера
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Логировать тело запроса/ответа
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor) // Добавляем логгер
        .build()

    // Создание экземпляра Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient) // Используем настроенный OkHttpClient (опционально)
        .addConverterFactory(GsonConverterFactory.create()) // Указываем Gson как конвертер
        .build()

    // Создание реализации интерфейса API
    val apiService: ZippopotamApiService = retrofit.create(ZippopotamApiService::class.java)
}