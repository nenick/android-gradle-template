# Rapid start development and test
with Android Studio, Gradle, Espresso, Robolectric, AndroidAnnotations, RoboCoP, JaCoCo

[![Build Status](https://travis-ci.org/nenick/android-gradle-template.svg?branch=master)](https://travis-ci.org/nenick/android-gradle-template) **& UnitTest** [![Coverage Status](https://coveralls.io/repos/nenick/android-gradle-template/badge.png)](https://coveralls.io/r/nenick/android-gradle-template)

***Wishes, improvements and discussions about the stuff here are welcome***

[see also wiki for more help](https://github.com/nenick/android-gradle-template/wiki)

## Getting Started

***until next release of novodes test plugin, we use our own custom version***

For gradle-android-test-plugin:0.9.9-SNAPSHOT run Scripts/install-custom-gradle-test-plugin.sh

***clone, attach phone (or start emulator), run Scripts/run-tests.sh in project root***

***import to android studio, start development***

**unit tests:** gradlew :UnitTestsRobolectric:testDebug

* append :UnitTestsRobolectric:jacocoTestReport for coverage report

**component tests:** gradlew :ComponentTestsRobolectric:testDebug

* append :ComponentTestsRobolectric:jacocoTestReport for coverage report

**acceptance tests:** gradlew :AndroidSample:connectedAndroidTest

* replace by :AndroidSample:connectedCheck for coverage report

## Test variants

**unit test**
Test with mocks for all class dependencies.
For testing direct database operations i don't like to mock the real database. Database is fast
enough for unit tests and writing cursor mocks need much effort. Database tests are done here too.

**component test**
Put app components under test. It's your decision, what you call a component. Good approaches maybe
Activities, Fragments, (any other ideas?), ...

***experimental***
For apps designed with single activity to handle all fragments. Try writing tests as component test
like you will write they in espresso. This should work and is very fast, but not suggested by google
coding guides and not from the robolectric programmer.

**acceptance tests**
Instrument your app with espresso to show that your app may handle the basic work flows correctly
on one or all your device variants.

## Test results

* build/index.html (**should collect all reports, current is miss the AndroidSample module reports **)
* UnitTestsRobolectric/build/test-report/debug/index.html (**unit test**)
* UnitTestsRobolectric/build/reports/jacoco/test/html/index.html (**unit test coverage**)
* ComponentTestsRobolectric/build/test-report/debug/index.html (**component test**)
* ComponentTestsRobolectric/build/reports/jacoco/test/html/index.html (**component test coverage**)
* AndroidSample/build/reports/androidTests/connected/index.html (**acceptance tests**)
* AndroidSample/build/reports/coverage/debug/index.html (**acceptance tests coverage**)

## Test support in IDE

This project template is developed with AndroidStudio and will only support the newest versions.

Test run in IDE is a bit tricky:  [see wiki for description](https://github.com/nenick/android-gradle-template/wiki/Tests-in-Android-Studio---IntellJ)

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

## Features wish

* ui test coverage (is done when novoda test plguin is updated)
* ui test screen shots with spoon, include dialogs
* collect all test reports nicely
* all nice tools from Quality-Tools-for-Android (pmd, checkstyle, ..)
* test server communication with wire mock (for component test and ui tests)
* generate database content at same time like android annotations
* Sonar for code statistics
* RoboCoP + SQLiteCipher

## Milestones

1. (done) working setup android studio + gradle + espresso + robolectric
2. (done) extended app sample with database access
3. (done) test automation with code coverage
4. (progress) decent test coverage with unit, component and acceptance tests
* (plan) http calls with android annotations + wiremock
* (plan) )...

## Scripts

* run-tests.sh execute all test variants

## Project structure

AndroidSample

* src/main/java/com/example/
    * **activities** activity classes
    * **adapters** data adapters with cursor loader
    * **fragments** fragment classes
    * **managers** wraps content provider usage
    * **receivers**
    * **services**
    * **viewmodel** model classes for your views
* src/gen/com/example/
    * **database** (generated) database tables and helpers
    * **provider** (generated) provider for your database content
    * **model** (generated) helper classes to access cursor content
* src/test/java/
    * **com/example** tests with espresso
* build/source/apt/<buildvariant>/
    * **com/example** (generated) classes with AndroidAnnotations

## See also my inspiration sources

[Quality-Tools-for-Android](https://github.com/stephanenicolas/Quality-Tools-for-Android)

[deckard-gradle](https://github.com/robolectric/deckard-gradle)

[android-tdd-playground](https://github.com/pestrada/android-tdd-playground)
