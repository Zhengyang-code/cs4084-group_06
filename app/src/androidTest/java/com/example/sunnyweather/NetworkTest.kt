package com.example.sunnyweather.logic.network

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class NetworkTest {

    @Test
    fun testSearchPlaces() = runBlocking {
        val response = SunnyWeatherNetwork.searchPlaces("beijing")

        // 检查响应是否包含“北京”
        assertNotNull(response)
        assertTrue("did not return any data", response.places.isNotEmpty())
        assertTrue("no beijing weather data", response.places.any { it.name.contains("北京") })
    }

    @Test
    fun testGetRealtimeWeather() = runBlocking {
        val lng = "116.40"
        val lat = "39.90"
        val realtime = SunnyWeatherNetwork.getRealtimeWeather(lng, lat)

        assertNotNull(realtime)
        assertTrue("data not found", realtime.result.realtime.temperature > -100)
    }

    @Test
    fun testGetDailyWeather() = runBlocking {
        val lng = "116.40"
        val lat = "39.90"
        val daily = SunnyWeatherNetwork.getDailyWeather(lng, lat)

        assertNotNull(daily)
        assertTrue("not enough number of return days", daily.result.daily.temperature.size > 1)
    }
}
