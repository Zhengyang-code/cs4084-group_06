package com.example.sunnyweather.ui.weather

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.sunnyweather.R
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString

@RunWith(AndroidJUnit4::class)
class UITest {

    private fun launchWeatherActivity(): ActivityScenario<WeatherActivity> {
        val intent = Intent(ApplicationProvider.getApplicationContext(), WeatherActivity::class.java).apply {
            putExtra("location_lng", "8.507239")
            putExtra("location_lat", "47.376886")
            putExtra("place_name", "Limerick")
        }
        return ActivityScenario.launch(intent)
    }

    @Test
    fun testForecastItemsAreDisplayed() {
        launchWeatherActivity()

        // forecastLayout 中动态添加 forecast_item 子项，我们测试是否至少显示了一个
        onView(withId(R.id.forecastLayout))
            .check(matches(hasMinimumChildCount(1)))
    }

    @Test
    fun testSwipeRefreshIsDisplayed() {
        launchWeatherActivity()
        onView(withId(R.id.swipeRefresh)).check(matches(isDisplayed()))
    }

    @Test
    fun testSwipeDownTriggersRefresh() {
        launchWeatherActivity()

        // 执行下拉动作
        onView(withId(R.id.swipeRefresh)).perform(swipeDown())

        // 再检查温度是否仍然显示，表示刷新后的数据可见
        onView(withId(R.id.currentTemp)).check(matches(isDisplayed()))
    }


    @Test fun testPlaceNameIsDisplayed() {
        launchWeatherActivity()
        onView(withId(R.id.placeName)).check(matches(withText(containsString("Limerick"))))
    }

    @Test fun testCurrentTempIsDisplayed() {
        launchWeatherActivity()
        onView(withId(R.id.currentTemp)).check(matches(withText(containsString("℃"))))
    }

    @Test fun testCurrentSkyIsDisplayed() {
        launchWeatherActivity()
        onView(withId(R.id.currentSky)).check(matches(isDisplayed()))
    }

    @Test fun testCurrentAQIIsDisplayed() {
        launchWeatherActivity()
        onView(withId(R.id.currentAQI)).check(matches(isDisplayed()))
    }

}
