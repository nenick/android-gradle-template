# Rapid start development and test
with Android Studio, Gradle, Espresso, Robolectric, AndroidAnnotations, RoboCoP, JaCoCo

[![Build Status](https://travis-ci.org/nenick/android-gradle-template.svg?branch=master)](https://travis-ci.org/nenick/android-gradle-template) **& UnitTest** [![Coverage Status](https://coveralls.io/repos/nenick/android-gradle-template/badge.png)](https://coveralls.io/r/nenick/android-gradle-template)

***Wishes, improvements and discussions about the stuff here are welcome***

## Choose your favourite way

Looks like we need no plugins anymore. With the new experimental unit test feature we can easy use robolectric. See the example https://github.com/nenick/AndroidStudioAndRobolectric or read a step by step guide http://nenick-android.blogspot.de/2015/02/android-studio-110-beta-4-and.html

### Tests in a seperated module

Switch to the branch https://github.com/nenick/android-gradle-template/tree/novoda for more informations

### Tests in the same module

I'm prefer https://github.com/JCAndKSolutions/android-unit-test to support robolectric because it integrates much better through the extra created IDE Plugin.

### More extended example with a clean architecture concept

https://github.com/nenick/AndroidAppDevelopment

## Getting Started

### Check if the project and tests works on your machine

***clone, attach phone (or start emulator), run `Scripts/run-tests.sh` from project root***

### Check if you get this project to work with andorid studio

***import to android studio, start development, press play***

## Test support in IDE

install the plugin https://github.com/evant/android-studio-unit-test-plugin you can install the plugin through the plugin installer in Android Studio

This project template support AndroidStudio.

## Tests on command line

**unit tests:** `./gradlew testNormalDebug`

* append `jacocoReport` for coverage report ***can't be combined with the integration tests***

**integration tests:** `./gradlew testIntegrationDebug`

* append `jacocoReport for coverage report ***can't be combined with the unit tests***

**acceptance tests:** `./gradlew connectedAndroidIntegrationTest`

* replace by `:App:connectedCheck` for coverage report

### rest example
Currently RestActivity example fails when no server is reachable.

* start `Scripts/start-wiremock.sh`
* kill  `Scripts/start-wiremock.sh kill`

## Test results

* build/index.html (**robolectric & unit tests**) *(should collect all reports, current is miss the some reports)*
* App/build/test-coverage-report/index.html (**unit test coverage**)
* App/build/test-coverage-report/index.html (**integration test coverage**)
* App/build/reports/androidTests/connected/index.html (**acceptance tests**)
* App/build/reports/coverage/debug/index.html (**acceptance tests coverage**)


## Last tests done with

* Android Studio 1.0.2
* Gradle Build Tools 1.0.0
* Gradle 2.1.1

## Features done

* Gradle + AndroidStudio as development Enironment
* Robolectric for unit tests
    * Mockito
    * Code coverage with JaCoCo
    * Code coverage with Teamcity
* Robolectric for component tests
    * Code coverage with JaCoCo
    * Code coverage with Teamcity
* FEST Android assertions
* Espresso for acceptance tests
* [AndroidAnnotations](http://androidannotations.org/) generate dependency injection
* [RoboCoP](https://github.com/mediarain/RoboCoP) generate database management
* Shortcut: jump between test and implementation with default short cut
* [Travis](https://travis-ci.org/) CI runs all test variants
* [Coveralls](https://coveralls.io/) shows unit test code coverage [coveralls-gradle-plugin](https://github.com/kt3k/coveralls-gradle-plugin)


## Scripts

* run-tests.sh execute all test variants
* run-tests-fast.sh execute just the robolectric tests
* run-tests-travis.sh do all steps necessary for full automatic builds, like install custom dependencies
