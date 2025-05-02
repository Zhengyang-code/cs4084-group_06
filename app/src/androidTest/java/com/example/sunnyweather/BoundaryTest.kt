package com.example.sunnyweather

import org.junit.Test
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking

class BoundaryTest {
    @Test
    fun testSearchPlaces_boundaryValues() = runBlocking {
        // Empty string - should return empty or fail
        try {
            val result = SunnyWeatherNetwork.searchPlaces("")
            assertTrue("should return empty", result.places.isEmpty())
        } catch (e: Exception) {
            println("The empty string test passes and an exception is thrown: ${e.message}")
        }

        // Minimum valid input: one Chinese character
        val result1 = SunnyWeatherNetwork.searchPlaces("北")
        assertTrue("Single character not matched", result1.places.isNotEmpty())

        // Very long string
        val longQuery = "北京".repeat(100)
        try {
            val result2 = SunnyWeatherNetwork.searchPlaces(longQuery)
            assertTrue("Very long searches should return empty", result2.places.isEmpty())
        } catch (e: Exception) {
            println("The overlong string test passed: ${e.message}")
        }
    }

    @Test
    fun testRealtimeWeather_withExtremeCoordinates() = runBlocking {
        // Legal boundaries: maximum latitude and longitude
        val result = SunnyWeatherNetwork.getRealtimeWeather("180.0", "90.0")
        assertNotNull(result)
        assertTrue(result.result.realtime.temperature > -100)

        // Illegal longitude and latitude (theoretically, the API should reject it)
        try {
            SunnyWeatherNetwork.getRealtimeWeather("200.0", "95.0")
            fail("Illegal coordinates should throw an exception")
        } catch (e: Exception) {
            println("Illegal coordinates test passed: ${e.message}")
        }
    }


}