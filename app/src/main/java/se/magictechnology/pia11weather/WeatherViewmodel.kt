package se.magictechnology.pia11weather

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import okio.IOException

class WeatherViewmodel : ViewModel() {

    private val client = OkHttpClient()

    val currentCityWeather: MutableLiveData<WeatherData> by lazy {
        MutableLiveData<WeatherData>()
    }

    fun loadWeather(context : Context) {

        var cityname = getCurrentCity(context)

        if(cityname == null) {
            return
        }



        // Hämta från api
        // https://goweather.herokuapp.com/weather/Malm%C3%B6
        /*
        var tempWeather = WeatherData()
        tempWeather.cityName = "Malmö"
        tempWeather.temperature = "+7 °C"
        tempWeather.wind = "20 km/h"
        tempWeather.description = "Light rain, drizzle and rain"

        currentCityWeather.value = tempWeather
        */

        val req = Request.Builder().url("https://goweather.herokuapp.com/weather/" + cityname).build()

        client.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("PIA11DEBUG", "HÄMTNING INTE OK")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    var responseString = response.body!!.string()

                    val weather = Json { ignoreUnknownKeys = true}.decodeFromString<WeatherData>(responseString)

                    weather.cityName = cityname

                    viewModelScope.launch(Dispatchers.Main) {
                        currentCityWeather.value = weather
                    }

                }
            }
        })


    }


    // TODO: Undvika använda context här
    fun addCity(context : Context, cityname : String) {

        val sharedPref = context.getSharedPreferences("se.magictechnology.pia11weather.shared", Context.MODE_PRIVATE)

        var savedCities = sharedPref.getStringSet("cities", mutableSetOf<String>())

        savedCities!!.add(cityname)

        val editor = sharedPref.edit()
        editor.putStringSet("cities", savedCities)
        editor.commit()

    }

    fun loadcities(context : Context) : List<String> {

        val sharedPref = context.getSharedPreferences("se.magictechnology.pia11weather.shared", Context.MODE_PRIVATE)

        var savedCities = sharedPref.getStringSet("cities", mutableSetOf<String>())

        return savedCities!!.toList()
    }

    fun setCurrentCity(context : Context, cityname : String) {
        val sharedPref = context.getSharedPreferences("se.magictechnology.pia11weather.shared", Context.MODE_PRIVATE)

        val editor = sharedPref.edit()
        editor.putString("currentcity", cityname)
        editor.commit()
    }

    fun getCurrentCity(context : Context) : String? {
        val sharedPref = context.getSharedPreferences("se.magictechnology.pia11weather.shared", Context.MODE_PRIVATE)

        return sharedPref.getString("currentcity", null)
    }
}