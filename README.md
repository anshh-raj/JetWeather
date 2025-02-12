# JetWeather
This weather app, developed using **Jetpack Compose** and **Kotlin**, offers an intuitive and feature-rich platform for users to get real-time weather updates for any location worldwide. The app fetches weather data from the **OpenWeather API** via **Retrofit**, providing accurate and comprehensive weather information at the user's fingertips.

### Key Features:

1. **Weather Data Retrieval**: Users can search for weather details by entering the **latitude** and **longitude** of any location. The app provides up-to-date information on temperature, humidity, wind speed, weather conditions, and more.

2. **Favorites Tab**: Users can save their favorite locations, which are easily accessible through the **Favorites** tab. This feature allows users to quickly switch between different locations and view their weather updates without having to search each time.

3. **Room Database Integration**: All saved favorite locations and their corresponding weather data are stored in a local **Room Database**.

4. **Temperature Units Toggle**: In the **Settings** tab, users can choose to view the weather data in either **Celsius** or **Fahrenheit**. This toggle allows for greater customization, ensuring that users can view the data in their preferred unit of measurement.

5. **MVVM Architecture & State Management**: The app follows the **MVVM (Model-View-ViewModel)** design pattern, ensuring a clean separation between the user interface, business logic, and data management. Proper **state management** is used to handle loading, error, and success states effectively, ensuring a smooth and responsive user experience.

6. **Jetpack Compose for UI**: The app’s UI is built using **Jetpack Compose**, allowing for a declarative and responsive design. The Compose framework provides a flexible and modern approach to building the app’s interface.

7. **Seamless User Experience**: With **Retrofit** for efficient API calls and **Room Database** for local data storage, the app ensures that weather information is always up-to-date, easily accessible, and persists across sessions. Whether users are at home or on the go, the app provides a reliable and responsive experience.

By leveraging **Jetpack Compose**, **Kotlin**, **Room Database**, and **MVVM**, the app delivers a robust and scalable solution for weather tracking. With the ability to search for weather based on latitude and longitude, save favorite locations, and toggle between Celsius and Fahrenheit, users are empowered to manage their weather updates in a way that suits their needs. The app's clean architecture, real-time data fetching, and smooth UI make it an ideal choice for anyone looking to stay informed about the weather wherever they go.
