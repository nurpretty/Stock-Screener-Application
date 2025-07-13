**Stock Screener Android App**
A simple Android application that displays a list of stocks with their company information, current prices, and allows users to mark favorites and search through the stock list.

**Features**
a)Display stock list with company names and current prices
b)Mark/unmark stocks as favorites
c)Search functionality to filter stocks by company name or symbol
d)Color-coded price changes (green for positive, red for negative)
e)Company logos

**Setup Instructions**
1.Prerequisites
-Android Studio Arctic Fox or later
-Minimum SDK: API 24 (Android 7.0)
-Target SDK: API 35
-Kotlin 1.8+

2.Installation
-Clone or download this project
-Open the project in Android Studio
-Wait for Gradle sync to complete
-Run the app on an emulator or physical device

3.Dependencies
-Material Design Components for UI
-Gson for JSON parsing
-Glide for image loading
-AndroidX Lifecycle components for MVVM

**App Structure**
1.Architecture
- It follows MVVM (Model-View-ViewModel) architecture pattern:
app/src/main/java/com/example/stockscreener/
├── data/
│   ├── model/              # Data classes (Stock, StockPrice, Price)
│   ├── repository/         # Data management and business logic
│   └── local/              # Local data source (JSON file parsing)
├── ui/
│   ├── adapter/            # RecyclerView adapter for stock list
│   ├── viewmodel/          # ViewModel and ViewModelFactory
│   └── MainActivity.kt     # Main UI controller
└── assets/stocks.json      # Stock data source

2.Key Components
a)Data Layer
Stock.kt: Data model representing a stock with price information
StockDataSource.kt: Handles reading and parsing JSON data from assets
StockRepository.kt: Manages stock data, favorites, and search functionality

b)UI Layer
MainActivity.kt: Main activity handling user interactions
StockAdapter.kt: RecyclerView adapter for displaying stock list
StockViewModel.kt: Manages UI state and handles business logic

c)Features Implementation
Search: Real-time filtering using TextWatcher
Favorites: Local state management with toggle functionality
Price Display: Color-coded backgrounds and trend indicators
Logo Loading: Async image loading with Glide and error handling

**Technical Notes**
Logo URL Changes
Issue Encountered: The original logo URLs in the provided data were returning 404 errors:
Original: https://storage.googleapis.com/iexcloud-hl37opg/api/logos/AAPL.png
Error: "NoSuchBucket" - The specified bucket does not exist
Solution: Replaced with Clearbit Logo API for reliable logo loading:
Updated: https://logo.clearbit.com/apple.com

This change ensures:
-Reliable logo loading without 404 errors
-Consistent fallback behavior when logos are unavailable
-Better user experience with actual company logos

All core functionality remains unchanged - only the logo URLs were updated to working endpoints.

**Trade-offs and Future Improvements**
1.Trade-offs Made
Static Data: Uses local JSON file instead of live API for simplicity
Memory Storage: Favorites reset on app restart (no persistence)
Basic Error Handling: Simple fallbacks without detailed error states

2.Future Improvements
Data Persistence: Save favorites using SharedPreferences or database
Real-time Updates: Integrate with live stock price APIs
Advanced Features: Price charts, notifications, and portfolio tracking
