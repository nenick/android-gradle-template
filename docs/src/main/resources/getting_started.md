

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

When you are on Test Artifact Unit Tests then you don't get the possibility to start instrumented tests.