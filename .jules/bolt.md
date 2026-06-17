## 2024-05-24 - Avoid PackageManager queries in Room Flow collectors
**Learning:** In MVVM with Room, Room Flow collectors run on the `viewModelScope`'s default dispatcher (Main). Performing expensive operations like `PackageManager.getApplicationLabel()` inside a `collect` block will block the main thread and re-run on every database update, causing massive UI jank during list toggles.
**Action:** Pre-calculate and cache expensive external properties (like app names from `PackageManager`) using `withContext(Dispatchers.IO)` before combining them with reactive database flows.
