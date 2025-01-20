# BudgetBuddy <img src="./logo.png" alt="BudgetBuddy Logo" height="40" style="vertical-align: middle;"/>


**BudgetBuddy** is a finance management Android app built with Kotlin that helps users efficiently track their income and expenses. The app integrates real-time currency data through an API, allows users to visualize transaction locations on a map, and features an MLKit-based receipt scanning functionality to simplify the transaction entry process.

## Author ✍️

This project was created by **[bulish](https://github.com/bulish)**. Feel free to reach out or contribute to the project!

## Installation 🔧

To run the app locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/bulish/BudgetBuddy.git
2. Open the project in **Android Studio**

3. Set up API keys in `local.properties`:
   ```bash
   google_maps_api_key=YOUR_API_KEY
   baseurldevel="https://v6.exchangerate-api.com/v6/YOUR_API_KEY/"
   baseurlproduction="https://v6.exchangerate-api.com/v6/YOUR_API_KEY/"
4. Sync the project with Gradle files.

5. Run the app on an Android emulator or device.
6. Configure permissions:
   - Ensure that the device has permission to use the camera. You need to allow the app to access the camera for the receipt scanning functionality to work properly.


## Features 🚀
- **Track Transactions**: Add income and expense transactions with detailed information.
- **Real-Time Currency Conversion**: Get live updates on exchange rates with the integrated API.
- **Map Integration**: Visualize the locations where transactions took place on an interactive map.
- **Receipt Scanning**: Use MLKit to scan receipts and automatically extract transaction data for easy entry.


## Technologies Used ⚙️
- **Kotlin**: Main programming language used for Android development.
- **Mapbox**: For displaying transaction locations on a map.
- **MLKit**: For scanning receipts and extracting transaction data.
- **Currency API**: For fetching real-time exchange rates.

## Contributing 🤝

I welcome contributions to the project! If you find a bug or want to add new features, please follow these steps:

1. Fork the repository.
2. Create a new branch.
3. Make your changes.
4. Submit a pull request with a description of your changes.
