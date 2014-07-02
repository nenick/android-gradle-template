# Rapid start development and test
with Android Studio, Gradle, Espresso, Robolectric, AndroidAnnotations, RoboCoP, JaCoCo

Travis has no build tools 20.0.0 yet, maybe later i will try workaround https://github.com/travis-ci/travis-ci/issues/2470

[![Build Status](https://travis-ci.org/nenick/android-gradle-template.svg?branch=master)](https://travis-ci.org/nenick/android-gradle-template) **& UnitTest** [![Coverage Status](https://coveralls.io/repos/nenick/android-gradle-template/badge.png)](https://coveralls.io/r/nenick/android-gradle-template)

***Wishes, improvements and discussions about the stuff here are welcome***

[see also wiki for more help](https://github.com/nenick/android-gradle-template/wiki)

## Last tests done with

* Android Studio 0.8.1
* Gradle Build Tools 0.12.+
* Gradle 1.12

## Getting Started

***until next release of novodes test plugin [with this pull request](https://github.com/novoda/gradle-android-test-plugin/pull/11) , we use our own custom version*** 

For gradle-android-test-plugin:0.9.9-SNAPSHOT run `Scripts/install-custom-gradle-test-plugin.sh`

### Check if the project and tests works on your machine

***clone, attach phone (or start emulator), run `Scripts/run-tests.sh` from project root***

### Check if you get this project to work with andorid studio

***import to android studio, start development, press play***

## Test support in IDE

This project template support AndroidStudio.

Run tests in Android Studio need some configuration:  [see wiki for description](https://github.com/nenick/android-gradle-template/wiki/Tests-in-Android-Studio---IntellJ)

## Tests on command line

**unit tests:** `./gradlew :UnitTestsRobolectric:testDebug`

* append `:UnitTestsRobolectric:jacocoTestReport` for coverage report

**component tests:** `./gradlew :ComponentTestsRobolectric:testDebug`

* append :ComponentTestsRobolectric:jacocoTestReport for coverage report

**acceptance tests:** `./gradlew :AndroidSample:connectedAndroidTest`

* replace by `:AndroidSample:connectedCheck` for coverage report

### rest example
Currently RestActivity example fails when no server is reachable. 

* start `Script/start-wiremock.sh`
* kill  `Script/start-wiremock.sh kill`

## Test results

* build/index.html *(should collect all reports, current is miss the AndroidSample module reports)*
* UnitTestsRobolectric/build/test-report/debug/index.html (**unit test**)
* UnitTestsRobolectric/build/reports/jacoco/test/html/index.html (**unit test coverage**)
* ComponentTestsRobolectric/build/test-report/debug/index.html (**component test**)
* ComponentTestsRobolectric/build/reports/jacoco/test/html/index.html (**component test coverage**)
* AndroidSample/build/reports/androidTests/connected/index.html (**acceptance tests**)
* AndroidSample/build/reports/coverage/debug/index.html (**acceptance tests coverage**)

## Features done

* Gradle + AndroidStudio as development Enironment
* Use [novoda/gradle-android-test-plugin](https://github.com/novoda/gradle-android-test-plugin) to use Robolectric in submodule
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
