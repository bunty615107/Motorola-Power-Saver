## 2026-06-09 - AllowlistViewModel PackageManager Performance
**Learning:** Calling `PackageManager.getApplicationLabel()` repeatedly inside a Flow `collect` block causes unnecessary IPC calls and performance bottlenecks when the UI state updates, especially for lists.
**Action:** Pre-compute expensive PackageManager lookups outside the Flow collection when the underlying data (installed apps) hasn't changed, mapping them into memory first.
