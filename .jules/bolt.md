## 2024-05-24 - Avoid PackageManager IPC on Main Thread and inside Flows
**Learning:** Calling `PackageManager.getInstalledApplications` and specifically `PackageManager.getApplicationLabel` requires IPC and disk I/O. Doing this on the main thread (during ViewModel initialization) causes UI stuttering. Furthermore, doing it inside a `.collect` block means the expensive label lookup is repeated *every single time* the flow emits (e.g., when a user toggles an app allowlist state), leading to compounding performance degradation on large app lists.
**Action:** Always offload `PackageManager` calls to `Dispatchers.IO`. Extract and cache the results (like `Pair(packageName, appName)`) *before* entering reactive flow collection blocks so that state updates only require cheap in-memory mapping.
## 2024-06-12 - DigitalClock Recomposition Optimization
**Learning:** Coroutine loops driving UI state in Compose (like clocks) often wake up the CPU and trigger recomposition at much higher frequencies than the UI actually needs to display.
**Action:** Always align the `delay()` duration in Compose `LaunchedEffect` loops with the exact granularity of the displayed data (e.g., delay until the next minute boundary if only hours/minutes are shown) to save battery and reduce UI thread load.
## 2026-06-09 - AllowlistViewModel PackageManager Performance
**Learning:** Calling `PackageManager.getApplicationLabel()` repeatedly inside a Flow `collect` block causes unnecessary IPC calls and performance bottlenecks when the UI state updates, especially for lists.
**Action:** Pre-compute expensive PackageManager lookups outside the Flow collection when the underlying data (installed apps) hasn't changed, mapping them into memory first.
