## 2024-06-12 - DigitalClock Recomposition Optimization
**Learning:** Coroutine loops driving UI state in Compose (like clocks) often wake up the CPU and trigger recomposition at much higher frequencies than the UI actually needs to display.
**Action:** Always align the `delay()` duration in Compose `LaunchedEffect` loops with the exact granularity of the displayed data (e.g., delay until the next minute boundary if only hours/minutes are shown) to save battery and reduce UI thread load.
