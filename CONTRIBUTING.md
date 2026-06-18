# Contributing to Extreme Saver Mode

First off, thank you for considering contributing to Extreme Saver Mode! It's people like you that make this app an ultra-aggressive battery-saving powerhouse.

## How to Contribute

### Reporting Bugs
If you find a bug, please create an issue using the Bug Report template. Please include as much information as possible, including:
* Steps to reproduce the bug
* Expected behavior
* Motorola device model
* Android version
* App version
* Screenshots or videos (if applicable)

### Suggesting Enhancements
Have an idea to make Extreme Saver Mode even better? Create an issue using the Feature Request template. Please provide:
* A clear and concise description of what the problem is.
* A description of the proposed solution.
* Any alternatives you've considered.
* Additional context or screenshots.

### Submitting Pull Requests
1. Fork the repo and create your branch from `main`.
2. Follow the branch naming convention: `feature/<name>`, `fix/<name>`, or `docs/<name>`.
3. If you've added code that should be tested, add tests.
4. Ensure the test suite passes (`./gradlew test`).
5. Run the linter and ensure your code passes (`./gradlew lintDebug`).
6. Update documentation as necessary.
7. Issue that pull request!

### Branch Naming Convention
Please use the following prefixes for your branches:
* `feature/` - for new features
* `fix/` - for bug fixes
* `docs/` - for documentation updates

### Commit Message Format
We follow the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) specification.
Example: `feat: add new battery saving policy`
Example: `fix: resolve crash on startup`

### Kotlin Code Style Guidelines
* Follow standard Kotlin coding conventions.
* **Architecture:** We strictly follow **Clean Architecture (MVVM)**.
  * Ensure separation of concerns between UI, Domain, and Data layers.
  * Use ViewModels to handle UI state and business logic.
  * Use Dagger Hilt for dependency injection.

### Jetpack Compose Best Practices
* **State Management:** Hoist state when possible.
* **Performance:** Avoid using polling loops (e.g., `LaunchedEffect` with `delay()`) within Jetpack Compose UI components to prevent unnecessary CPU wakeups. Instead, rely on ViewModels to observe and pass down state from global, event-driven updates.
* **Composables:** Keep composable functions small and focused.
* **Side Effects:** Handle side effects correctly using standard Compose side-effect handlers.
