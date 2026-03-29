# KotakNeoApp

A native Android stock trading application that integrates with the Kotak Neo trading platform. The app enables users to authenticate, stream real-time market data via WebSocket, search and place orders for stocks, and view their portfolio — all built with a modern multi-module clean architecture.

## Features

- **Authentication** — Multi-step login flow with mobile number, UCC, TOTP, and MPIN verification. Session management with token and SID persistence.
- **Real-Time Market Streaming** — Live stock price updates via WebSocket connection to a backend relay server hosted on AWS EC2. Subscribe/unsubscribe to symbols on the fly.
- **Order Placement** — Search stocks from a locally cached scrip master (Room DB), place buy/sell orders with configurable order type, quantity, and price.
- **Portfolio** — View current positions and holdings.
- **Bottom Navigation** — Home (market stream), Trade (place orders), and Portfolio tabs using Material 3 NavigationBar.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Clean Architecture (multi-module) |
| DI | Hilt |
| Networking | Retrofit + OkHttp |
| WebSocket | OkHttp WebSocket |
| Serialization | Kotlinx Serialization + Gson |
| Local Storage | Room + DataStore |
| Async | Kotlin Coroutines + StateFlow |
| Build | Gradle (Kotlin DSL) + Version Catalog |

## Module Structure

```
KotakNeoApp/
├── app/                        # Main application module, MainActivity, navigation
├── core/
│   ├── common/                 # Shared utilities
│   ├── model/                  # Domain models
│   ├── network/                # Retrofit API, WebSocket client, network error handling
│   ├── data/                   # Room database, repositories, DAOs
│   ├── datastore/              # Local preferences (DataStore)
│   └── ui/                     # Shared Compose UI components
├── feature/
│   ├── login/                  # Login, MPIN verification, auth flow UI
│   └── marketstream/           # Market streaming, order placement, portfolio UI
└── gradle/
    └── libs.versions.toml      # Dependency version catalog
```

## Architecture

```
UI (Compose) → ViewModel → DataSource / Repository → Retrofit API / WebSocket / Room DB
```

- **feature/** modules own the UI screens and ViewModels
- **core/network** handles all API calls, WebSocket connections, and error handling with a custom `NetworkResponse` sealed class and Retrofit call adapter
- **core/data** manages local persistence with Room (stock scrip master cache) and exposes repositories
- Hilt wires everything together across modules

## Getting Started

### Prerequisites
- Android Studio Ladybug or later
- JDK 17+
- Android SDK 34+

### Build & Run
1. Clone the repo
   ```bash
   git clone https://github.com/SangSund0824/KotakNeoAndroid.git
   ```
2. Open in Android Studio
3. Sync Gradle and run on an emulator or device
