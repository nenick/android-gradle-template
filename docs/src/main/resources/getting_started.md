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

**acceptance tests:** `./gradlew :appIt:connectedAndroidTest`

* replace by `:appIt:connectedCheck` for coverage report

**combined coverage report:** ./gradlew jacocoTestReport

## Android Studio - Espresso

When you are on `Test Artifact: Unit Tests` then you don't get the possibility to start instrumented tests.
Switch to `Test Artifact: Instrumentation Tests` and then start/create test run configuration.
Later this created configuration can still be used when you are on `Test Artifact: Unit Tests` without changing it.