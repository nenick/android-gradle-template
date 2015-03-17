# Rapid start development and test
with Android Studio, Gradle, Espresso, Robolectric, AndroidAnnotations, RoboCoP, JaCoCo

[![Build Status](https://travis-ci.org/nenick/android-gradle-template.svg)](https://travis-ci.org/nenick/android-gradle-template) **& UnitTest** [![Coverage Status](https://coveralls.io/repos/nenick/android-gradle-template/badge.png)](https://coveralls.io/r/nenick/android-gradle-template)

***Wishes, improvements and discussions about the stuff here are welcome***

[see also wiki for more help](https://github.com/nenick/android-gradle-template/wiki)

* A more minimalistic robolectric example can be found at https://github.com/nenick/AndroidStudioAndRobolectric
* Basic examples how to use gradle-android-test-plugin see https://github.com/novoda/gradle-android-test-plugin

## Last tests done with

* Android Studio 1.1.0
* Gradle Build Tools 1.1.2
* Gradle 2.2.1
* Novoda gradle-android-test-plugin 0.10.4 (Not yet released)

## Getting Started

Until next novoda plugin release use the Script/install-custom-gradle-test-plugin.sh to install the necessary plugin version

### Check if the project and tests works on your machine

***clone, attach phone (or start emulator), run `Scripts/run-tests.sh` from project root***

### Check if you get this project to work with andorid studio

***import to android studio, start development, press play***

## Test support in IDE

This project template support AndroidStudio.

Run tests in Android Studio need some configuration:  [see wiki for description](https://github.com/nenick/android-gradle-template/wiki/Tests-in-Android-Studio---IntellJ)

## Tests on command line

**unit tests:** `./gradlew :App:testDebug`

* append `:App:jacocoTestReport` for coverage report

**component tests:** `./gradlew :AppComponentTests:testDebug`

* append `:AppComponentTests:jacocoTestReport` for coverage report

**acceptance tests:** `./gradlew :App:connectedAndroidTest`

* replace by `:App:connectedCheck` for coverage report

### rest example
Currently RestActivity example fails when no server is reachable. 

* start `Script/start-wiremock.sh`
* kill  `Script/start-wiremock.sh kill`

## Test results

* build/index.html *(should collect all reports, current is miss the AndroidSample module reports)*
* App/build/test-report/debug/index.html (**unit test**)
* App/build/reports/jacoco/jacoco.html (**unit test coverage**)
* AppComponentTests/build/test-report/debug/index.html (**component test**)
* AppComponentTests/build/reports/jacoco/test/html/index.html (**component test coverage**)
* App/build/reports/androidTests/connected/index.html (**acceptance tests**)
* App/build/reports/coverage/debug/index.html (**acceptance tests coverage**)

## Features done

* Gradle + AndroidStudio as development Enironment
* Robolectric for unit tests at jvm instead on devices
    * Code coverage with JaCoCo
* [novoda/gradle-android-test-plugin](https://github.com/novoda/gradle-android-test-plugin) to use Robolectric in submodule
    * Code coverage with JaCoCo
* FEST Android assertions
* Espresso for acceptance tests
* [AndroidAnnotations](http://androidannotations.org/) generate dependency injection
* [RoboCoP](https://github.com/mediarain/RoboCoP) generate database management
* [Travis](https://travis-ci.org/) CI runs all test variants
* [Coveralls](https://coveralls.io/) shows unit test code coverage [coveralls-gradle-plugin](https://github.com/kt3k/coveralls-gradle-plugin)

## Scripts

* run-tests.sh execute all test variants
* run-tests-fast.sh execute just the robolectric tests
* run-tests-travis.sh do all steps necessary for full automatic builds, like install custom dependencies
