package com.example.retrofittigranyan
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var countryEditText: EditText
    private lateinit var postalCodeEditText: EditText
    private lateinit var fetchButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Убедитесь, что у вас есть соответствующий layout файл

        // Инициализация ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Привязка View
        countryEditText = findViewById(R.id.et_country_code)
        postalCodeEditText = findViewById(R.id.et_postal_code)
        fetchButton = findViewById(R.id.btn_fetch)
        resultTextView = findViewById(R.id.tv_result)

        // Настройка обработчика нажатия кнопки
        fetchButton.setOnClickListener {
            val country = countryEditText.text.toString().trim()
            val postalCode = postalCodeEditText.text.toString().trim()

            if (country.isNotEmpty() && postalCode.isNotEmpty()) {
                resultTextView.text = "Загрузка..." // Показываем индикатор загрузки
                viewModel.fetchPostalCodeInfo(country, postalCode)
            } else {
                Toast.makeText(this, "Введите код страны и почтовый индекс", Toast.LENGTH_SHORT).show()
            }
        }

        // Наблюдение за данными
        viewModel.postalCodeInfo.observe(this) { info ->
            if (info != null) {
                // Форматируем и отображаем результат
                val placesInfo = info.places.joinToString(separator = "\n") { place ->
                    " - ${place.placeName}, ${place.state} (${place.stateAbbreviation}) [${place.latitude}, ${place.longitude}]"
                }
                resultTextView.text = """
                    Страна: ${info.country} (${info.countryAbbreviation})
                    Индекс: ${info.postCode}
                    Места:
                    $placesInfo
                """.trimIndent()
            } else {
                // Если info == null, но нет ошибки, возможно, стоит показать "Нет данных"
                // Но в нашем ViewModel мы устанавливаем null при ошибке, так что обработка ошибки ниже позаботится об этом
            }
        }

        // Наблюдение за ошибками
        viewModel.errorMessage.observe(this) { error ->
            if (error != null) {
                resultTextView.text = "" // Очищаем предыдущий результат
                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            }
        }
    }
}