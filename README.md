
# Coin Ranking App
CoinRanking is an Android application that allows users to explore various cryptocurrencies. It features a clean, intuitive UI and offers functionality such as bookmarking, searching, and viewing detailed information about cryptocurrencies. The app supports both light and dark themes and provides a seamless user experience with efficient data handling using modern Android development best practices.

## Features
### Architecture & Data Handling
- **MVVM Architecture**: Ensures clean separation of concerns and easier maintainability.
- **Single Source of Truth**: Data is persisted in the local database while fetching updates from the network, providing a seamless offline experience.
- **Flow & Coroutines**: Utilizes Kotlin Coroutines and Flow for handling asynchronous tasks, ensuring a smooth, responsive user interface.
- **Retrofit for Networking**: API calls are handled using Retrofit, providing a fast and reliable connection to the CoinRanking API.
- **Local Database**: Implements Room to provide local storage with a single source of truth.

### UI/UX
- **Dark Theme**: A fully supported dark mode to enhance user experience in low-light environments.
- **Drawer Navigation**: Easy access to the setting section of the app via a navigation drawer.
- **Bottom Navigation**: Convenient navigation across the main sections of the app.
- **ViewPager**: Smooth swipe-based navigation between different fragments or sections.

### Functionality
- **Bookmarking**: Users can bookmark their favorite cryptocurrencies for quick access.
- **Search Functionality**: Allows users to quickly find cryptocurrencies and exchanges.

## Screenshots
<p align="center">
   <img src="screenshots/main light.png" alt="Menu Screenshot" width="400"/>
   <img src="screenshots/detail light.png" alt="Setting Screenshot" width="400"/>
</p>
<p align="center">
   <img src="screenshots/main dark.png" alt="Menu Screenshot" width="400"/>
   <img src="screenshots/drawer dark.png" alt="Setting Screenshot" width="400"/>
</p>


## Tech Stack
- **Programming Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Asynchronous Handling**: Kotlin Coroutines, Flow
- **Networking**: Retrofit
- **Local Database**: Room
- **UI Components**: ViewPager, Drawer, Bottom Navigation
- **Theme Support**: Light/Dark Mode
- **API**: CoinRanking API

## API Integration
This app uses the **CoinRanking API** to fetch data about cryptocurrencies and exchanges. API calls are handled through Retrofit, and data is stored locally in Room for offline access.

### Key Endpoints:
- `/coins`: Fetch the list of cryptocurrencies.
- `/coin/{uuid}`: Fetch detailed information about a specific cryptocurrency.
- `/search-suggestions`: Fetch the list of filtered coins.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
