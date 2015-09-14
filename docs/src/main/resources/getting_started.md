[Back to Index](index.md)

# Getting Started

## Start Wiremock

Without the example app sync process will fail.

Execute from project root

* Linux/Mac `tools/src/main/resources/start-wiremock.sh`
* Windows `java -jar wiremock\wiremock-1.57-standalone.jar --port 1337 --https-port 1338 --verbose --root-dir 'wiremock\src\main\resources'`

The app get your current ip at compile time and then try to connect to your wiremock instance.

## Start from commandline

Start an emulator or connect with device.
Then on commandline navigate into the project root and execute from there following script and check that all works.

* Linux/Mac `tools/src/main/resources/test-all-with-coverage.sh`
* Windows `tools\src\main\resources\test-all-with-coverage.bat`

#### Gradle Commands

**unit tests:** `./gradlew app:test`

* append `app:jacocoTestReport` for coverage report

**component tests:** `./gradlew appCt:test`

* append `appCt:jacocoTestReport` for coverage report

**acceptance tests:** `./gradlew appIt:connectedAndroidTest` *(Use `connectedCheck` until bug closed <https://code.google.com/p/android/issues/detail?id=183936>)*

* replace by `appIt:connectedCheck` for coverage report

**combined coverage report:** `./gradlew jacocoTestReport` after you run all tests


## Start with Android Studio

Import project into Android Studio.

#### Build Variants settings

Change the build variants test artifact type to unit tests.
Since we can use a test module for instrumentation tests we don't need to switch between them anymore.

#### Android Studio - Espresso

When you are on `Test Artifact: Unit Tests` then you don't get the possibility to start instrumented tests.
Switch to `Test Artifact: Instrumentation Tests` and then start/create test run configuration.
Later this created configuration can still be used when you are on `Test Artifact: Unit Tests` without changing it.

I hope this behavior can be adjusted so that the test module only support instrumented tests and we need no more to switch the test artifact type <https://code.google.com/p/android/issues/detail?id=183931>

#### Android Project View

On the left top about your project structure you can switch between different project content views.
I like the minimal Android View, but at project view you can see more files.
Switch there to you preferred style.

*Some modules only are modules instead of simple folders to support the Android Project View. Without they would not be visible.*

---

[Back to Index](index.md)