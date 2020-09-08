# Test Your Android App

For basic introduction into testing please see https://developer.android.com/training/testing/fundamentals.

## Test targets

**Small: Module Unit Tests** are testing small code pieces without android dependencies.
They exist in `every module` where necessary inside the `test` directory.

**Medium: Module UI Tests** are testing the behaviours of the UI components.
External dependencies/services are mocked.
They exist in `every module` where necessary inside the `androidTest` directory.

**Medium: App Component Tests** runs a simulated app with a mocked backend to verify all use cases.
They exist in `app-tests-component` module.

**Large: App Integration Tests** runs your App with a mocked backend to verify all user flows.
They exist in `app-tests-integration` module.

**Large: App System Tests** runs your App with a real hosted backend to verify the main use cases.
They exist in `app-tests-system` module.

**Large: App Release Tests** instruments your release APK to verify it is working.
All other test targets are commonly run against a debug variant.
Here we check finally that the release APK does start and work.
They exist in `app-tests-release` module.

## `buildSrc` Unit Tests

These tests does not run when you just call `test` on root project but they are always checked when you build your app.

*Run tests from command line*

    ./gradlew -i -p buildSrc test