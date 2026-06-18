# Community Submissions Guide

This document contains ready-to-use content for promoting Extreme Saver Mode across various open-source and developer communities.

---

## 1. AWESOME-ANDROID SUBMISSION
**Repository:** [JStumpp/awesome-android](https://github.com/JStumpp/awesome-android)
**Section:** Battery & Power (or Utility)

**Exact Markdown Line to Add:**
```markdown
* [Extreme Saver Mode](https://github.com/YOUR_USERNAME/extreme-saver-mode) - Ultra-aggressive battery-saving launcher and background policy enforcement engine for critical situations.
```

**Pull Request Details:**
*   **PR Title:** Add Extreme Saver Mode to Battery & Power
*   **PR Description:**
    ```markdown
    Adding Extreme Saver Mode to the Battery & Power list.

    It is an open-source, aggressive system-level Android application that transforms the device into a minimalist "Soft Kiosk" to dramatically extend battery life during critical situations by strictly limiting background processes, network syncing, and notifications. Built with Kotlin, Clean Architecture, and Jetpack Compose.
    ```

---

## 2. AWESOME JETPACK COMPOSE SUBMISSION
**Repository:** [androiddevnotes/awesome-jetpack-compose-android-apps](https://github.com/androiddevnotes/awesome-jetpack-compose-android-apps)
**Section:** Open Source Projects

**Exact Markdown Line to Add:**
```markdown
* [Extreme Saver Mode](https://github.com/YOUR_USERNAME/extreme-saver-mode) - Ultra-aggressive battery-saving launcher utilizing a pitch-black Compose UI and system-level power overrides.
```

**Pull Request Details:**
*   **PR Title:** Add Extreme Saver Mode
*   **PR Description:**
    ```markdown
    Hello! I'd like to submit Extreme Saver Mode to the awesome Jetpack Compose list.

    It's an ultra-aggressive battery-saving launcher built entirely with Jetpack Compose. It uses an OLED-optimized (True Black) Compose UI and leverages Clean Architecture, Hilt, and Room under the hood. It serves as a great example of a system-level utility app built with modern Android development practices.
    ```

---

## 3. XDA DEVELOPERS FORUM POST
**Subforum:** Motorola Android Development (e.g., Motorola Edge 50 Pro)

**Title:** [APP][14.0+] Extreme Saver Mode - Aggressive Battery Saver & Soft Kiosk

**Post Body:**
```bbcode
[B][SIZE=5]Extreme Saver Mode[/SIZE][/B]

Modern smartphones, especially those with 144Hz OLED screens like the Edge 50 Pro, drain battery rapidly even when idle. When you have 10% battery left and need your phone to stay alive for an emergency, standard battery savers just don't cut it.

Extreme Saver Mode solves the "Critical Battery Panic." It is an ultra-aggressive, offline-first system-level Android application that transforms your device into a minimalist "Soft Kiosk" to dramatically extend battery life.

[B][SIZE=4]Features:[/SIZE][/B]
[LIST]
[*] [B]Soft Kiosk Launcher:[/B] Replaces your default home screen with a distraction-free, OLED-optimized True Black (#000000) UI.
[*] [B]Aggressive Power Throttling:[/B] Drops screen timeout to 15s, disables auto-brightness, forces low brightness (~20%), and globally disables background account syncing.
[*] [B]Dynamic App Allowlisting:[/B] Uses an AccessibilityService to instantly block and kick you out of non-allowlisted apps.
[*] [B]Notification Suppression:[/B] Swallows incoming notifications from blocked apps so the screen doesn't wake up unnecessarily (Calls/SMS always go through).
[*] [B]Secure Exit:[/B] Requires biometric authentication (Fingerprint/Face/PIN) to exit the mode.
[*] [B]Boot Persistence:[/B] Auto-relaunches upon reboot if active before shutdown.
[*] [B]Real-time Battery Analytics:[/B] Tracks live drain rates without polling!
[/LIST]

[B][SIZE=4]Screenshots:[/SIZE][/B]
[I][Placeholder: Insert images of the black dashboard, allowlist screen, and battery stats][/I]

[B][SIZE=4]Download:[/SIZE][/B]
[URL="https://github.com/YOUR_USERNAME/extreme-saver-mode/releases/latest"]Download latest APK from GitHub Releases[/URL]
[URL="https://github.com/YOUR_USERNAME/extreme-saver-mode"]Source Code (GitHub)[/URL]

[B][SIZE=4]Setup Instructions:[/SIZE][/B]
1. Install the APK and set as Default Home App.
2. Grant Accessibility Service, Notification Access, and Modify System Settings.
3. Add essential apps to your Allowlist.

[B][SIZE=4]Changelog:[/SIZE][/B]
[CODE]
v1.0.0
- Initial release
- Implemented core Soft Kiosk Launcher
- Added power throttling overrides
- Accessibility and Notification suppression implemented
[/CODE]

Let me know what you think, and please report any bugs on GitHub!
```

---

## 4. REDDIT POST TEMPLATES

### A. r/motorola Version
**Suggested Flair:** App/Software
**Title:** I built an "Extreme Saver Mode" specifically to maximize OLED battery life on the Edge 50 Pro during low-battery emergencies.

**Body:**
> Hey everyone,
>
> I love my Edge 50 Pro, but the battery can drain quickly on standby with that 144Hz screen and background syncing. When I hit 10% and really need the phone to survive until I get home, standard battery saver modes aren't aggressive enough.
>
> So, I built an open-source app called **Extreme Saver Mode**.
>
> When activated, it replaces your launcher with a completely black (OLED-pixel-off) dashboard. It overrides system settings (forces 15s screen timeout, kills background sync), and uses an accessibility service to strictly limit you to a custom allowlist of essential apps (like Phone/SMS). It even suppresses notifications from other apps so they don't wake the screen.
>
> It's completely free and open-source. Would love for other Moto users to test it out and let me know if it helps in a pinch!
>
> **GitHub & APK:** [Link to Repo/Releases]

### B. r/androidapps Version
**Suggested Flair:** [DEV]
**Title:** [DEV] Extreme Saver Mode - An aggressive "Soft Kiosk" app that overrides system power settings to survive critical low-battery scenarios.

**Body:**
> Hey r/androidapps,
>
> I built an open-source utility aimed at solving the "Critical Battery Panic."
>
> Standard Android battery saver modes throttle CPU/GPU, but they don't stop you from opening heavy apps or waking your screen for random notifications. **Extreme Saver Mode** takes a different approach.
>
> When enabled, it acts as a launcher replacement. It provides a pitch-black, minimalist UI to save OLED power. More importantly, it uses an `AccessibilityService` to enforce an app allowlist—try to open Instagram while in Extreme Saver, and it instantly kicks you back to the black launcher. It also drops screen timeout to 15s, disables auto-brightness, and uses a `NotificationListenerService` to swallow non-essential notifications so your screen stays off.
>
> It's a hardcore "break glass in case of emergency" battery saver (and honestly doubles nicely as a dopamine detox/focus mode).
>
> Would love your feedback on the UX and features!
>
> **GitHub:** [Link to Repo]

### C. r/Android Version
**Suggested Flair:** APPreciation (Post in the Saturday thread)
**Title:** Extreme Saver Mode - An open-source, system-level power throttling launcher

**Body:**
> **App Name:** Extreme Saver Mode
>
> **Description:** An ultra-aggressive, offline-first system-level Android application designed to drastically extend battery life during critical situations. It acts as a launcher replacement (Soft Kiosk) with a True Black OLED UI. It enforces an app allowlist via Accessibility services, suppresses background notifications to prevent screen wake-ups, and aggressively overrides system power settings (15s timeout, kills background sync).
>
> **Google Play:** Not on Play Store yet (due to deep system permissions)
>
> **Source/APK:** [GitHub Link]

---

## 5. DEV.TO BLOG POST OUTLINE

**Title:** How I Built an Aggressive Android Battery Saver (and Used Jules AI to Optimize It)
**Meta Description:** A deep dive into building Extreme Saver Mode, a system-level Android launcher using Compose and Clean Architecture, and how AI helped optimize its performance to zero-polling.
**Tags:** `#android`, `#kotlin`, `#opensource`, `#performance`

**Article Outline:**

1. **Introduction: The "Critical Battery Panic"**
   * The problem with modern smartphones and standard battery savers.
   * The concept of a "Soft Kiosk" and true OLED black interfaces.
   * Overview of Extreme Saver Mode.

2. **The Architecture of Extreme Saver Mode**
   * Why I chose Clean Architecture (MVVM).
   * Leveraging Jetpack Compose for a True Black UI.
   * Using Dagger Hilt for dependency injection and Room for offline persistence.

3. **Overriding the System: The Core Mechanics**
   * *The Launcher:* Replacing the home screen.
   * *AccessibilityService:* Dynamically enforcing the app allowlist (kicking users out of unapproved apps).
   * *NotificationListenerService:* Suppressing wake-ups by swallowing non-essential notifications.

4. **The Performance Challenge: Monitoring Battery Without Draining It**
   * The initial naive approach (polling loops).
   * Why polling defeats the purpose of an extreme battery saver.

5. **Enter Jules AI: Optimizing for Zero-Polling**
   * How I used Jules AI to refactor the battery analytics.
   * The shift from `LaunchedEffect` with `delay()` to an event-driven architecture using `BroadcastReceiver`.
   * Passing state down through ViewModels to Compose UI without unnecessary CPU wake-ups.
   * The measurable impact on background battery drain.

6. **Lessons Learned & Future Scope**
   * What building a system-level app taught me about Android's power management.
   * Future ideas: Digital Wellbeing/Detox mode, granular hardware toggles.

7. **Conclusion & Open Source Call to Action**
   * Links to the GitHub repository.
   * Invitation for developers to contribute or try the app.
