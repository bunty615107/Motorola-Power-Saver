## 2024-05-24 - Avoid PackageManager queries in Room Flow collectors
**Learning:** In MVVM with Room, Room Flow collectors run on the `viewModelScope`'s default dispatcher (Main). Performing expensive operations like `PackageManager.getApplicationLabel()` inside a `collect` block will block the main thread and re-run on every database update, causing massive UI jank during list toggles.
**Action:** Pre-calculate and cache expensive external properties (like app names from `PackageManager`) using `withContext(Dispatchers.IO)` before combining them with reactive database flows.
## 2024-05-24 - [Over-Recomposition of Clock UI causing Battery Drain]
**Learning:** `DigitalClock.kt` was refreshing every second (`delay(1_000L)`) even though the UI only displayed minutes. In a standard app, this might just waste CPU cycles, but in an "Extreme Saver Mode" designed to aggressively conserve battery, these 60 unnecessary recompositions per minute unnecessarily wake the main thread and defeat the purpose of the application. The timer loop precision must match the UI precision.
**Action:** Always align `LaunchedEffect` timer intervals with the finest granularity of the data being displayed. If showing "HH:mm", calculate the time until the next minute boundary rather than blindly ticking every second.
## 2024-05-24 - Avoid PackageManager IPC on Main Thread and inside Flows
**Learning:** Calling `PackageManager.getInstalledApplications` and specifically `PackageManager.getApplicationLabel` requires IPC and disk I/O. Doing this on the main thread (during ViewModel initialization) causes UI stuttering. Furthermore, doing it inside a `.collect` block means the expensive label lookup is repeated *every single time* the flow emits (e.g., when a user toggles an app allowlist state), leading to compounding performance degradation on large app lists.
**Action:** Always offload `PackageManager` calls to `Dispatchers.IO`. Extract and cache the results (like `Pair(packageName, appName)`) *before* entering reactive flow collection blocks so that state updates only require cheap in-memory mapping.
## 2024-06-12 - DigitalClock Recomposition Optimization
**Learning:** Coroutine loops driving UI state in Compose (like clocks) often wake up the CPU and trigger recomposition at much higher frequencies than the UI actually needs to display.
**Action:** Always align the `delay()` duration in Compose `LaunchedEffect` loops with the exact granularity of the displayed data (e.g., delay until the next minute boundary if only hours/minutes are shown) to save battery and reduce UI thread load.
## 2026-06-09 - AllowlistViewModel PackageManager Performance
**Learning:** Calling `PackageManager.getApplicationLabel()` repeatedly inside a Flow `collect` block causes unnecessary IPC calls and performance bottlenecks when the UI state updates, especially for lists.
**Action:** Pre-compute expensive PackageManager lookups outside the Flow collection when the underlying data (installed apps) hasn't changed, mapping them into memory first.

## 2024-05-25 - Avoid redundant polling UI update fallback in BatteryStatus
**Learning:** Polling UI update loop in Compose composables runs redundantly. In `BatteryStatus.kt`, `LaunchedEffect` loop constantly refreshes every 30s. This acts as a fallback but forces a recomposition loop that defeats the Extreme Saver app goal. Since UI state comes from the `BatteryChangedReceiver`, updating values locally bypasses the overarching pattern, unnecessarily wakes up the main thread and introduces unwanted UI lags.
**Action:** Always extract values from ViewModels which observes global states updated directly by `BroadcastReceivers` rather than polling inside Composable functions.

## 2024-06-25 - CI/CD Pipeline Performance
**Learning:** Adding a caching mechanism for Gradle dependencies in GitHub Actions (`actions/cache`) significantly reduces build times by preventing the download of the same dependencies on every single run, thereby conserving CI minutes and providing faster feedback.
**Action:** Always include a caching step for dependency managers (like Gradle) when configuring CI workflows.
## 2026-06-18 - README Rewrite
**Learning:** README acts as an essential documentation hub. Ensuring that performance optimization highlights are clear and visually structured effectively communicates the project’s specific aims.
**Action:** When working on open-source READMEs, incorporate precise examples of optimizations and clear installation/build steps.
