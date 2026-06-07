# Extreme Saver Mode for Motorola Edge 50 Pro

**Extreme Saver Mode** is an ultra-aggressive, offline-first system-level Android application designed primarily for the Motorola Edge 50 Pro (Android 14+). It transforms the device into a minimalist, highly efficient "Soft Kiosk" that dramatically extends battery life during critical situations by strictly limiting background processes, network syncing, screen usage, and notifications.

---

## 📱 What It Is

It is a specialized launcher and background policy enforcement engine. When activated, it replaces the standard Android home screen with a pitch-black, OLED-optimized dashboard. Under the hood, it intercepts system events, aggressively manages power settings, and restricts device usage to a user-defined allowlist of essential applications (like Phone and SMS). 

The app employs **Clean Architecture (MVVM)**, **Jetpack Compose** for UI, **Hilt** for Dependency Injection, and **Room** for offline persistence.

---

## ⚡ What It Can Do

- **Soft Kiosk Launcher:** Replaces your default home screen with a distraction-free, minimalist UI (True Black `#000000` to turn off OLED pixels).
- **Aggressive Power Throttling:** Automatically overrides system settings to maximize efficiency:
  - Drops screen timeout to 15 seconds.
  - Disables auto-brightness and forces a low brightness level (~20%).
  - Globally disables background account syncing.
- **Dynamic App Allowlisting (Policy Enforcement):** Uses an Android `AccessibilityService` to monitor the foreground app. If the user tries to open an app that is *not* on the approved list, it instantly kicks them back to the Extreme Saver home screen.
- **Notification Suppression:** Uses a `NotificationListenerService` to swallow incoming notifications from blocked apps, preventing the screen from waking up unnecessarily. Phone calls and SMS notifications are always let through.
- **Secure Exit Protocol:** Exiting the mode requires biometric authentication (Fingerprint/Face/PIN), ensuring the mode isn't accidentally dismissed.
- **Boot Persistence:** Automatically relaunches itself upon device reboot if the mode was active when the device was turned off.
- **Real-time Battery Analytics:** Tracks live battery drain rates, charging status, and temperature without polling (purely event-driven to save power).

---

## 🛠️ What Problem It Solves

Modern smartphones, especially those with 144Hz OLED screens like the Motorola Edge 50 Pro, drain battery rapidly even when idle due to background syncs, rogue apps, and frequent notification wake-ups. The built-in "Battery Saver" modes often just throttle CPU/GPU and restrict some background tasks, but they don't stop the user from doom-scrolling or opening battery-heavy apps.

**Extreme Saver Mode solves the "Critical Battery Panic".** 

When you have 10% battery left and need your phone to stay alive for the next 12 hours to receive an emergency call or text, this app guarantees it.

---

## 🔮 What It Is Going To Solve (Future Scope)

While currently fully functional, future iterations of this architecture could solve:
- **Digital Wellbeing & Dopamine Detox:** By extending the Kiosk mode, it can be used not just for battery saving, but as a hardcore "Focus Mode" to cure smartphone addiction.
- **Granular Hardware Toggles:** Auto-toggling 5G to 4G/3G, turning off Bluetooth, and forcing a 60Hz refresh rate (requires root or ADB permissions on some firmwares).
- **Automated Triggers:** Activating automatically when the battery hits a specific threshold or based on location (e.g., "Activate when I am out hiking in the wilderness").

---

## 🚀 How You Can Use It

### Installation
1. Install the provided `app-debug.apk` onto your device.
2. Open the app. The system will prompt you to set it as your **Default Home App**.

### Initial Setup (Permissions)
To achieve maximum power savings, the app requires deep system access. Go to the **Settings** screen within the app and grant:
1. **Accessibility Service:** Allows the app to block non-allowlisted apps.
2. **Notification Access:** Allows the app to suppress non-essential notifications.
3. **Modify System Settings:** Allows the app to dim the screen and disable sync.

### Daily Usage
1. **Activate:** Tap the "Enable Extreme Saver" button. Your screen will go black, showing only the time and battery status.
2. **Manage Allowlist:** Swipe to the "Allowlist" screen. Here, you can select exactly which apps you need (e.g., WhatsApp, Maps). Everything else is blocked.
3. **Monitor Battery:** Swipe to the "Battery" screen to see real-time drain rates and estimated survival time.
4. **Deactivate:** Tap the "Exit" button on the main dashboard. You will be prompted to authenticate with your fingerprint or PIN. Once verified, the standard Android launcher and normal power settings are restored.

---

## 💻 Development & Build Setup

### System Dependencies
To compile this project from source, you must have the following installed:
1. **Java Development Kit (JDK) 17 or 21**
2. **Android SDK Platform 34** (Android 14)
3. **Android SDK Build-Tools 34.0.0**

### Step-by-Step Build Instructions
1. **Install Java**: Ensure `JAVA_HOME` is set to your JDK 17/21 installation.
2. **Install Android SDK**: Use Android Studio or `sdkmanager` to install `platforms;android-34` and `build-tools;34.0.0`. Set your `ANDROID_HOME` environment variable.
3. **Configure Project**: Ensure a `local.properties` file exists in the project root containing the path to your SDK:
   ```properties
   sdk.dir=C\:\\Your\\Path\\To\\Android\\Sdk
   ```
4. **Build the APK**: Open a terminal in the project root and run:
   ```bash
   # On Windows
   .\gradlew.bat assembleDebug

   # On Mac/Linux
   ./gradlew assembleDebug
   ```
5. **Install**: Connect your device with USB Debugging enabled and run:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```
