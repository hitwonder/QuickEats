# QuickEats Application

## Screenshots

Here are some screenshots of the QuickEats application:

<table>
  <tr>
    <th>Home Screen</th>
    <th>Prodcut Detail Screen</th>
    <th>Order Summary Screen</th>
    <th>Payment Screen</th>
  </tr>
  <tr>
    <td><img src="https://github.com/hitwonder/QuickEats/blob/main/screenshots/1.png?raw=true" width="270" height="480""></td>
    <td><img src="https://github.com/hitwonder/QuickEats/blob/main/screenshots/2.png?raw=true" width="270" height="480"></td>
    <td><img src="https://github.com/hitwonder/QuickEats/blob/main/screenshots/3.png?raw=true" width="270" height="480"></td>
    <td><img src="https://github.com/hitwonder/QuickEats/blob/main/screenshots/4.png?raw=true" width="270" height="480"></td>
  </tr>
</table>



## Features

- User-friendly interface
- Item browsing and searching
- Cart functionality
- Geo-location Integration - Coming Soon

This repository contains a modern QuickEats app built with Android’s Jetpack components and Kotlin. The application allows users to browse and purchase items through an intuitive interface while maintaining a clean and scalable architecture. It follows SOLID principles, uses dependency injection (Hilt), and includes unit testing for critical parts of the logic.

### Features

- **Item Listings**: 
  - Browse a wide selection of products with detailed information about each item, including descriptions, prices, and availability.

- **Shopping Cart**: 
  - Add and remove items from the cart.
  - Dynamically view item count and total price.
  - Easily adjust quantities for each item in the cart.

- **Checkout Process**: 
  - Initiate the checkout process directly from the cart.
  - A simple and secure way to complete your purchase.

- **Clean Architecture**: 
  - The app follows modern Android architecture principles, including MVVM (Model-View-ViewModel), for maintainability and scalability.

- **Dependency Injection with Hilt**: 
  - The app is built with Hilt for dependency injection, enabling modular code and easier testing.

- **RecyclerView for Item Display**: 
  - Efficiently display large lists of products using RecyclerView, providing a smooth user experience.

- **CoordinatorLayout with Floating Action Button**: 
  - The app uses CoordinatorLayout to provide dynamic UI behavior, including a floating action button for adding items to the cart or checking out.

- **Unit Testing**: 
  - Unit tests are implemented for critical parts of the business logic to ensure app reliability and correctness.

## Prerequisites

- Android Studio 4.0 or higher
- JDK 8 or higher
- Gradle 6.1.1 or higher
- Kotlin 1.4 or higher

## Technologies Used

- **Kotlin**: Main programming language used for Android development.
- **Jetpack Navigation Component**: Handles in-app navigation between fragments.
- **LiveData and ViewModel**: Lifecycle-aware components for data handling.
- **RecyclerView**: Displays item lists with efficient memory usage.
- **Hilt**: Dependency injection for modular architecture and testing.
- **ConstraintLayout**: Flexible UI layout design.
- **CoordinatorLayout**: Provides behaviors like the floating action button and toolbars.


## Installation

To get started with this project, clone the repository and open it in Android Studio:

```bash
# Clone this repository
$ git clone https://github.com/yourusername/QuickEats-app.git

# Open the project in Android Studio
# Sync Gradle and run the app on an Android emulator or device
