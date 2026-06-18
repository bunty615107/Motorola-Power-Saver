## 2024-06-12 - DigitalClock Recomposition Optimization
**Learning:** Coroutine loops driving UI state in Compose (like clocks) often wake up the CPU and trigger recomposition at much higher frequencies than the UI actually needs to display.
**Action:** Always align the `delay()` duration in Compose `LaunchedEffect` loops with the exact granularity of the displayed data (e.g., delay until the next minute boundary if only hours/minutes are shown) to save battery and reduce UI thread load.
## 2026-06-09 - AllowlistViewModel PackageManager Performance
**Learning:** Calling `PackageManager.getApplicationLabel()` repeatedly inside a Flow `collect` block causes unnecessary IPC calls and performance bottlenecks when the UI state updates, especially for lists.
**Action:** Pre-compute expensive PackageManager lookups outside the Flow collection when the underlying data (installed apps) hasn't changed, mapping them into memory first.
