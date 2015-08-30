# Getting Started

Start an emulator or connect with device.
Then on commandline navigate into the project root and execute from there `tools/src/main/resources/test-all-with-coverage.sh`
and check that all works. (Works with Mac and Linux but Windows user must use `tools\src\main\resources\test-all-with-coverage.bat`)

With Android Studio import the project and start developing.

## Build Variants settings

Change the build variants test artifact type to unit tests.
Since we can use a test module for instrumentation tests we don't need to switch between them anymore.

## Gradle Commands

**unit tests:** `./gradlew :app:test`

* append `:app:jacocoTestReport` for coverage report

**component tests:** `./gradlew :appCt:test`

* append `:appCt:jacocoTestReport` for coverage report

**acceptance tests:** `./gradlew :appIt:connectedAndroidTest` (Use `connectedCheck` until bug closed <https://code.google.com/p/android/issues/detail?id=183936>)

* replace by `:appIt:connectedCheck` for coverage report

**combined coverage report:** ./gradlew jacocoTestReport

## Android Studio - Espresso

When you are on `Test Artifact: Unit Tests` then you don't get the possibility to start instrumented tests.
Switch to `Test Artifact: Instrumentation Tests` and then start/create test run configuration.
Later this created configuration can still be used when you are on `Test Artifact: Unit Tests` without changing it.

I hope this behavior can be adjusted so that the test module only support instrumented tests and we need no more to switch the test artifact type <https://code.google.com/p/android/issues/detail?id=183931>

### Android Project View

On the left top about your project structure you can switch between different project content views.
I like the minimal Android View, but at project view you can see more files.
Switch there to you preferred style.

*Some modules only are modules instead of simple folders to support the Android Project View. Without they would not be visible.*