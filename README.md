<div align="center">

# ⚡ Motorola Power Saver

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/your-username/extreme-saver-mode/actions)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![API Level](https://img.shields.io/badge/API-34%2B-blue.svg?logo=android)](https://android.com)
[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.9.22-purple.svg?logo=kotlin)](https://kotlinlang.org)

**An ultra-aggressive, offline-first system-level Android application designed primarily for the Motorola Edge 50 Pro (Android 14+).** It transforms the device into a minimalist, highly efficient "Soft Kiosk" that dramatically extends battery life during critical situations by strictly limiting background processes, network syncing, screen usage, and notifications.

</div>

---

## 📱 What It Is

It is a specialized launcher and background policy enforcement engine. When activated, it replaces the standard Android home screen with a pitch-black, OLED-optimized dashboard. Under the hood, it intercepts system events, aggressively manages power settings, and restricts device usage to a user-defined allowlist of essential applications (like Phone and SMS). 

---

## ⚡ Features & Performance Optimizations

This application has been relentlessly optimized to prevent battery drain. Key performance optimizations include:

- **Soft Kiosk Launcher:** Replaces your default home screen with a distraction-free, minimalist UI (True Black `#000000` to turn off OLED pixels).
- **Aggressive Power Throttling:** Automatically overrides system settings to maximize efficiency:
  - Drops screen timeout to 15 seconds.
  - Disables auto-brightness and forces a low brightness level (~20%).
  - Globally disables background account syncing.
- **Dynamic App Allowlisting (Policy Enforcement):** Uses an Android `AccessibilityService` to monitor the foreground app. If the user tries to open an app that is *not* on the approved list, it instantly kicks them back to the Extreme Saver home screen.
- **Notification Suppression:** Uses a `NotificationListenerService` to swallow incoming notifications from blocked apps, preventing the screen from waking up unnecessarily. Phone calls and SMS notifications are always let through.
- **Secure Exit Protocol:** Exiting the mode requires biometric authentication (Fingerprint/Face/PIN), ensuring the mode isn't accidentally dismissed.
- **Boot Persistence:** Automatically relaunches itself upon device reboot if the mode was active when the device was turned off.

### 🛠️ Core Optimizations
- **BatteryStatus Recomposition Fix:** Eliminated unnecessary UI redraws by properly stabilizing the battery status state.
- **DigitalClock 1/min Recomposition:** Reduced CPU wakeups by ensuring the digital clock only recomposes once per minute, avoiding expensive tick operations.
- **AllowlistViewModel PackageManager Caching:** Drastically improved allowlist loading times and reduced system calls by caching `PackageManager` results.
- **IO Thread Offloading:** Ensured all disk reads, writes, and database operations run completely off the main UI thread, preventing jank and saving power.

---

## 📸 Screenshots

<!-- TODO: Add screenshots here -->

---

## 🚀 Installation

### Download APK
1. Download the latest `app-debug.apk` from the [Releases](https://github.com/your-username/extreme-saver-mode/releases) page.
2. Install the APK onto your device.
3. Open the app. The system will prompt you to set it as your **Default Home App**.

### Build from Source
1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/extreme-saver-mode.git
   cd extreme-saver-mode
   ```
2. **Setup environment:**
   - Ensure you have **JDK 17 or 21** installed and `JAVA_HOME` is set.
   - Install **Android SDK Platform 34** and **Build-Tools 34.0.0**.
   - Set the `local.properties` file with your SDK path:
     ```properties
     sdk.dir=C\:\\Your\\Path\\To\\Android\\Sdk
     ```
3. **Build the APK:**
   ```bash
   # On Windows
   .\gradlew.bat assembleDebug

   # On Mac/Linux
   ./gradlew assembleDebug
   ```
4. **Install onto your device (with USB Debugging enabled):**
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

---

## 🛠️ Tech Stack

Built with modern Android development standards:
- **Language:** Kotlin
- **UI Toolkit:** Jetpack Compose
- **Architecture:** Clean Architecture (MVVM)
- **Dependency Injection:** Hilt
- **Persistence:** Room Database
- **Concurrency:** Kotlin Coroutines

---

## 🤝 Contributing

We welcome contributions! If you're interested in improving the app, optimizing performance further, or fixing bugs, please check out our [CONTRIBUTING.md](CONTRIBUTING.md) guide for more details on how to get started.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
