# KotakNeoApp

An Android trading application built with modern Android development practices, following a multi-module clean architecture approach.

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **Architecture:** MVVM + Clean Architecture (multi-module)
- **Dependency Injection:** Hilt
- **Networking:** Retrofit + OkHttp
- **Serialization:** Kotlinx Serialization + Gson
- **Local Storage:** Room + DataStore
- **Async:** Kotlin Coroutines
- **Build:** Gradle (Kotlin DSL) with Version Catalog

## Module Structure

```
KotakNeoApp/
├── app/                    # Main application module
├── core/
│   ├── common/             # Shared utilities
│   ├── model/              # Domain models
│   ├── network/            # Retrofit API, WebSocket, network utilities
│   ├── data/               # Repositories
│   ├── datastore/          # Local preferences (DataStore)
│   └── ui/                 # Shared UI components
├── feature/
│   ├── login/              # Authentication (Login, MPIN)
│   └── marketstream/       # Market data streaming, orders, portfolio
└── gradle/
    └── libs.versions.toml  # Dependency version catalog
```

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
