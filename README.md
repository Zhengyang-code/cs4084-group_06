 Weather Forecast App ("彩云天气" – Chinese API)
An Android weather forecast application developed using the Caiyun Weather ("彩云天气") API.
The app offers real-time localized weather conditions and travel guidance in Simplified Chinese,
as the app integrates a Chinese-only API.

All weather data and suggestions are in Simplified Chinese, as required by the selected API.

UI automatically darkens at night to reflect realistic day-night weather.


Key Features
This app supports the following 12 features:

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
