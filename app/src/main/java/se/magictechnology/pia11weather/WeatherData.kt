package se.magictechnology.pia11weather

import kotlinx.serialization.Serializable

@Serializable
class WeatherData {

    var cityName = ""

    var temperature = ""
    var wind = ""
    var description = ""

}