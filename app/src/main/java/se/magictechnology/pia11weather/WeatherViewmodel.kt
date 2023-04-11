package se.magictechnology.pia11weather

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WeatherViewmodel : ViewModel() {

    val currentCityWeather: MutableLiveData<WeatherData> by lazy {
        MutableLiveData<WeatherData>()
    }

    fun loadWeather() {

        // TODO: Hämta sparad stad i shared pref på telefon
        var cityname = "Eslöv"

        // Hämta från api
        // https://goweather.herokuapp.com/weather/Malm%C3%B6
        var tempWeather = WeatherData()
        tempWeather.cityName = "Malmö"
        tempWeather.temperature = "+7 °C"
        tempWeather.wind = "20 km/h"
        tempWeather.description = "Light rain, drizzle and rain"

        currentCityWeather.value = tempWeather

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
}