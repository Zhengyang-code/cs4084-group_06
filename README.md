# Smart Weather Forecast App

A sophisticated Android weather application built in Java with intelligent weather alerts, personalized recommendations, and real-time weather data integration using the Caiyun Weather API.

![App Screenshot](img_2.png)

## 🌟 Key Features

### Weather Information
- **Real-time Weather Display**: Current temperature, weather conditions, and detailed atmospheric data
- **7-Day Weather Forecast**: Comprehensive daily weather predictions with temperature ranges
- **Dynamic Weather Icons**: Visual representations based on Caiyun's skycon values (CLEAR_DAY, RAIN, CLOUDY, etc.)
- **Air Quality Index**: Real-time AQI data with health recommendations
- **Weather Alerts**: Intelligent notifications for severe weather conditions

### Smart User Experience
- **Intelligent Weather Notifications**: Personalized alerts based on weather conditions
- **City Management**: 
  - Search by city name (supports both Chinese and English)
  - Save multiple cities for quick access
  - Seamless switching between saved locations
- **Offline Capability**: Access cached weather data when offline
- **Real-time Refresh**: Pull-to-refresh functionality with visual feedback
- **Smart UI**: Automatic theme switching based on time of day

### Lifestyle Recommendations
- **Daily Suggestions** including:
  - 🌞 UV Index advisory
  - 🧣 Temperature comfort indicators
  - 👕 Clothing recommendations
  - 🚶 Outdoor activity suitability
- **Weather-Based Reminders**: Customized notifications for specific weather conditions
- **Historical Weather Data**: View weather patterns and trends

## 🛠️ Technology Stack

### Development Language
- **Java 11** - Primary development language
- **XML** - Layout files

### Architecture
- **MVVM** - Model-View-ViewModel architecture
- **Repository Pattern** - Data repository pattern
- **Observer Pattern** - Observer pattern for data updates

### Core Technologies
- **AndroidX** - Modern Android components
- **Retrofit2** - Network request library
- **Gson** - JSON parsing
- **SharedPreferences** - Local data storage
- **RecyclerView** - List display
- **SwipeRefreshLayout** - Pull-to-refresh functionality

### Third-party Libraries
```gradle
implementation 'com.squareup.retrofit2:retrofit:2.6.1'
implementation 'com.squareup.retrofit2:converter-gson:2.6.1'
implementation 'com.google.android.material:material:1.12.0'
implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'
```

## 🌐 API Integration

### Caiyun Weather API
This application integrates with the **Caiyun Weather API** (彩云天气) to provide accurate weather data:

- **API Endpoint**: `https://api.caiyunapp.com/v2.6/{token}/{longitude},{latitude}/weather`
- **Features**: Real-time weather, 7-day forecasts, air quality data
- **Language Support**: Chinese and English city names
- **Data Types**: Temperature, humidity, wind speed, air quality, weather alerts

### API Configuration
The API token is configured in `WeatherApplication.java`:
```java
public static final String TOKEN = "fJRKNUB6LbxMKPAu";
```

**Note**: This is a demo token. For production use, please register for your own API key at the [Caiyun Weather Dashboard](https://dashboard.caiyunapp.com/).

## 📂 Project Structure

```
app/src/main/java/com/example/weatherforecast/
├── MainActivity.java              # Main activity
├── WeatherApplication.java        # Application class
├── models/                        # Data models
│   ├── City.java
│   ├── Place.java
│   ├── Location.java
│   ├── Weather.java
│   ├── WeatherResponse.java
│   ├── RealtimeWeather.java
│   ├── DailyWeather.java
│   └── AirQuality.java
├── network/                       # Network layer
│   ├── ServiceCreator.java
│   ├── PlaceService.java
│   ├── WeatherService.java
│   └── WeatherNetwork.java
├── repository/                    # Data repository
│   └── Repository.java
├── dao/                          # Data access layer
│   └── PlaceDao.java
├── ui/                           # User interface
│   ├── PlaceFragment.java
│   ├── WeatherActivity.java
│   ├── adapter/
│   │   └── PlaceAdapter.java
│   └── viewmodel/
│       └── PlaceViewModel.java
├── service/                      # Service layer
│   └── WeatherNotificationService.java
└── utils/                        # Utility classes
    └── WeatherUtils.java
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or newer
- Android SDK 24+
- Java 11
- Internet connection for API calls

### Installation Steps

1. **Clone the Repository**
```bash
git clone https://github.com/Zhengyang-code/cs4084-group_06.git
cd cs4084-group_06
```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned directory and select it

3. **Configure API Key** (Optional)
   - Register for a free API key at [Caiyun Weather Dashboard](https://dashboard.caiyunapp.com/)
   - Replace the token in `WeatherApplication.java`:
   ```java
   public static final String TOKEN = "YOUR_API_KEY_HERE";
   ```

4. **Build and Run**
   - Sync Gradle dependencies
   - Connect an Android device or start an emulator
   - Click "Run" to build and install the app

### Permissions Required
- `INTERNET` - Network access for weather data
- `POST_NOTIFICATIONS` - Weather alerts (Android 13+)

## 🔧 Configuration

### API Limitations
The free tier of Caiyun Weather API:
- Supports up to 1000 requests per day
- Provides real-time weather and 7-day forecasts
- Includes air quality data and weather alerts
- Supports Chinese and English city names

### Fallback Mechanism
The app includes a robust fallback system:
- Uses mock data when API is unavailable
- Caches previous weather data for offline access
- Graceful error handling with user-friendly messages

## 🎨 Unique Features

### 1. Intelligent Weather Notifications
- **Smart Alert System**: Analyzes weather patterns to send relevant notifications
- **Personalized Content**: Weather-specific advice and recommendations
- **Vibration Alerts**: Ensures important weather information is not missed

### 2. Advanced Weather Analysis
- **Temperature Comfort Index**: Calculates optimal comfort levels
- **Clothing Recommendations**: Smart suggestions based on temperature and weather
- **Activity Suitability**: Determines if outdoor activities are recommended

### 3. Enhanced User Experience
- **Responsive Design**: Adapts to different screen sizes and orientations
- **Smooth Animations**: Fluid transitions and loading indicators
- **Intuitive Navigation**: Easy city switching and data refresh

## 📱 Usage Guide

### City Search
1. Tap the search icon in the main interface
2. Enter city name (Chinese or English)
3. Select from search results
4. Weather data will automatically load

### Weather Refresh
- Pull down on the main screen to refresh weather data
- Visual progress indicator shows refresh status
- Automatic updates every 30 minutes

### City Management
- Save frequently visited cities for quick access
- Switch between saved cities using the navigation menu
- Remove cities from favorites as needed

## 🤝 Contributing

We welcome contributions to improve the project!

### Development Guidelines
- Follow Java coding conventions
- Use meaningful variable and method names
- Add appropriate comments and documentation
- Keep code clean and maintainable
- Test thoroughly before submitting

### How to Contribute
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Team

**CS4084 Group 06** - University of Limerick, 2025

- Zhengyang Li
- Cunxin Yu  
- Song Wang
- Yilong Dong

## 📞 Support

For questions, issues, or suggestions:
- Create an issue on GitHub
- Contact the development team
- Check the documentation for common solutions

## 🔄 Version History

### v2.0.0 (Current)
- Complete Java rewrite
- Enhanced weather notification system
- Improved UI/UX design
- Real Caiyun Weather API integration
- Advanced weather analysis features

### v1.0.0 (Original Kotlin Version)
- Initial Kotlin implementation
- Basic weather functionality
- Simple UI design

---

**Note**: This project is developed for educational purposes. Please respect API usage limits and terms of service.
