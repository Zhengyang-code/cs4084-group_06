Weather Forecast App ("彩云天气" – Chinese API)
An Android weather forecast application developed using the Caiyun Weather ("彩云天气") API.
The app offers real-time localized weather conditions and travel guidance in Simplified Chinese,
as the app integrates a Chinese-only API.

All weather data and suggestions are in Simplified Chinese, as required by the selected API.

Key Features
This app supports the following 12 features:

UI automatically darkens at night to reflect realistic day-night weather.

Current Weather Display 
Shows temperature, weather condition, wind, humidity.

3-Day Overview: Yesterday, Today, Day-After-Tomorrow

Search by City Name 
Supports both Chinese (e.g., “上海”) and English (e.g., “Shanghai”) inputs.

Multi-City Management
Users can store, view, and switch between saved cities.

Dynamic Weather Icons
Icons are displayed based on Caiyun's skycon values, such as CLEAR_DAY, RAIN, CLOUDY.

Auto Night Mode UI
Automatically switches to darker theme in the evening or night time.

Offline Mode (Room Database)
Stores the latest weather data for each city in local database for offline viewing.

Pull-to-Refresh
Manual refresh using swipe-down gesture to update weather data.

Full Chinese Localization
Weather, city names, and all UI labels are in Simplified Chinese (as returned by the API).

Error Handling and Loading Feedback
Progress indicators during fetch, and user-friendly error prompts on failure.

Travel and Lifestyle Suggestions 
Displays daily suggestions such as:

🌞 UV Index 

🧣 Cold/Warm Indicator 

👕 Clothing Advice 

🚶 Outdoor Suitability 

Modular Architecture
Uses MVVM design with clean separation between API, database, and UI layers.

Powered by Caiyun Weather API
Chinese-only response

Provides: current conditions, yesterday/today/future day data, travel suggestions

Example API:

https://api.caiyunapp.com/v2.6/YOUR_API_KEY/LONGITUDE,LATITUDE/weather?alert=true&dailysteps=3
❗ The free tier of the API does not support GPS auto-detection or hourly/weekly forecasts.

🛠 Tech Stack
Language: Java

Architecture: MVVM

API Access: Retrofit2 + Gson

Database: Room

UI Components: RecyclerView, SwipeRefreshLayout

Icons & Theme: Day/Night responsive weather icons and dynamic backgrounds

🚀 Getting Started
1. Clone the Project
git clone https://github.com/Zhengyang-code/cs4084-group_06.git
cd cs4084-group_06
2. Open in Android Studio
Use Android Studio Arctic Fox or newer.

3. Add API Key
Register at https://dashboard.caiyunapp.com/ and add this to res/values/strings.xml:

<string name="caiyun_api_key">YOUR_API_KEY</string>
📂 Project Structure
app/
├── activities/
│   └── MainActivity.java
├── fragments/
│   ├── CurrentWeatherFragment.java
│   ├── ForecastFragment.java
│   └── LifestyleSuggestionFragment.java
├── model/
│   └── WeatherResponse.java
├── network/
│   └── WeatherService.java
├── adapter/
│   └── CityAdapter.java
├── database/
│   └── WeatherDao.java
└── res/
    ├── layout/
    ├── drawable/
    └── values/
📸 Screenshots
<img width="266" alt="62be5fa60eec548a2652629cd30537d" src="https://github.com/user-attachments/assets/76c5151a-858f-429b-8fd0-fc4edabdc146" />
<img width="272" alt="c508d26f20a58ac695e16b0747cd7e1" src="https://github.com/user-attachments/assets/4cb0a387-a21c-4933-94b3-d73ddf846dab" />
<img width="261" alt="4290cfb75b7c1f35b6a7dc8d43442e9" src="https://github.com/user-attachments/assets/bd2b8d3b-f25b-4347-84e0-7ab6fe2c6581" />



📄 License
This project is for academic use only and licensed under MIT.
See LICENSE file for full terms.

👨‍💻 Contributors
Zhengyang Li
Cunxin Yu
Song Wang
Yilong Dong

CS4084 Group 06

University of Limerick, 2025


