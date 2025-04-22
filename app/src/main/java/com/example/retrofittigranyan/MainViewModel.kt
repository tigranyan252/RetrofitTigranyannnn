package com.example.retrofittigranyan
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofittigranyan.data.model.PostalCodeInfo
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _postalCodeInfo = MutableLiveData<PostalCodeInfo?>()
    val postalCodeInfo: LiveData<PostalCodeInfo?> = _postalCodeInfo

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchPostalCodeInfo(countryCode: String, postalCode: String) {
        // Запускаем корутину в ViewModelScope, которая автоматически отменится при уничтожении ViewModel
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getPostalCodeInfo(countryCode, postalCode)

                if (response.isSuccessful) {
                    // Запрос успешен (код 2xx)
                    _postalCodeInfo.postValue(response.body()) // Получаем тело ответа (десериализованный объект PostalCodeInfo)
                    _errorMessage.postValue(null) // Сбрасываем ошибку
                } else {
                    // Запрос не успешен (например, 404 Not Found, 500 Server Error)
                    _postalCodeInfo.postValue(null)
                    _errorMessage.postValue("Ошибка: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                // Ошибка сети или десериализации
                _postalCodeInfo.postValue(null)
                _errorMessage.postValue("Ошибка сети: ${e.message}")
                // Здесь можно добавить более детальную обработку ошибок (например, UnknownHostException, SocketTimeoutException)
            }
        }
    }
}