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

    val apierror: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun loadWeather(context : Context) {
        loadWeather(context, null)
    }

    fun loadWeather(context : Context, addcityname : String?) {

        var cityname = getCurrentCity(context)

        if(addcityname != null) {
            cityname = addcityname
        }

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

                    Log.i("PIA11DEBUG", "FICK SVAR")

                    if(response.isSuccessful) {
                        Log.i("PIA11DEBUG", "GICK BRA")
                    } else {
                        Log.i("PIA11DEBUG", "GICK INTE BRA")
                    }

                    Log.i("PIA11DEBUG", response.code.toString())

                    var responseString = response.body!!.string()

                    Log.i("PIA11DEBUG", responseString)

                    val weather = Json { ignoreUnknownKeys = true}.decodeFromString<WeatherData>(responseString)

                    weather.cityName = cityname

                    if(weather.temperature == "") {
                        viewModelScope.launch(Dispatchers.Main) {
                            apierror.value = true
                        }
                    } else {
                        viewModelScope.launch(Dispatchers.Main) {
                            if(addcityname != null) {
                                saveCity(context, addcityname)
                            }
                            apierror.value = false
                            currentCityWeather.value = weather
                        }
                    }



                }
            }
        })


    }


    // TODO: Undvika använda context här
    fun addCity(context : Context, cityname : String) {

        loadWeather(context, cityname)

    }

    private fun saveCity(context: Context, cityname : String) {
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