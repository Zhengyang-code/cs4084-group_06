package com.example.sunnyweather

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork
import com.example.sunnyweather.ui.weather.WeatherActivity
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeviousTest {
    @Test
    fun testSearchPlaces_withSpecialCharacters() = runBlocking {
        val specialInputs = listOf("!@#", "北京!", "123北京", "'' or 1=1", "💥🔥🌪")

        for (input in specialInputs) {
            try {
                val result = SunnyWeatherNetwork.searchPlaces(input)
                println("enter [$input] return cities：${result.places.size}")
            } catch (e: Exception) {
                println("enter [$input] exception：${e.message}")
            }
        }
    }

    @Test
    fun testSearchPlaces_withInvalidToken() = runBlocking {
        // The simulation token is empty or wrong
        try {
            val result = SunnyWeatherNetwork.searchPlaces("北京")
            fail("An exception should have been thrown, but data was returned successfully")
        } catch (e: Exception) {
            println("Invalid request test passed：${e.message}")
        }
    }

    @Test
    fun testDoubleClickRefreshTriggersHandledProperly() {
        WeatherActivity()

        // Click navBtn twice in a row
        onView(withId(R.id.navBtn)).perform(click())
        onView(withId(R.id.navBtn)).perform(click())

        // Check if drawerLayout is still stable
        onView(withId(R.id.drawerLayout)).check(matches(isDisplayed()))
    }



}