# Rapid start development and test
with Android Studio, Gradle, Espresso, Robolectric, AndroidAnnotations, RoboCoP, JaCoCo

[![Build Status](https://travis-ci.org/nenick/android-gradle-template.svg?branch=master)](https://travis-ci.org/nenick/android-gradle-template) [![Coverage Status](https://coveralls.io/repos/nenick/android-gradle-template/badge.png)](https://coveralls.io/r/nenick/android-gradle-template)

***Wishes, improvements and discussions about the stuff here are welcome***

[see also wiki for more help](https://github.com/nenick/android-gradle-template/wiki)

## Getting Started

clone, import to android studio, start development.

**unit tests:** gradlew :UnitTestsRobolectric:testDebug

* append :UnitTestsRobolectric:jacocoTestReport for coverage report

**component tests:** gradlew :ComponentTestsRobolectric:testDebug

**acceptance tests:** gradlew :AndroidSample:connectedAndroidTest

* replace with :AndroidSample:connectedCheck for coverage report (works only with gradle build tools 0.10.+)

## codeCoverage with gradle build tools 0.10 (until novoda plugin is not updated)

* remove test modules from settings.gradle
* replace build tools 0.9.+ with 0.10.+
* insert at build-types -> debug: testCoverageEnabled = true

## Test variants

**unit test**
Test with mocks for all class dependencies. Common android methods are disabled e.g onCreate() at
the super classes. So you may test your own onCreate() method without throwing exceptions on super
onCreate() call.
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

* build/index.html (**all test reports collected**)
* UnitTestsRobolectric/build/reports/jacoco/test/html/index.html (**unit test**)
* UnitTestsRobolectric/build/test-report/debug/index.html (**unit test coverage**)
* ComponentTestsRobolectric/build/test-report/debug/index.html (**component test**)
* AndroidSample/build/reports/androidTests/connected/index.html (**acceptance tests**)

## IDE Support

This project template is developed with AndroidStudio and will only support the newest versions.

Test run in IDE is a bit tricky:  [see wiki for description](https://github.com/nenick/android-gradle-template/wiki/Tests-in-Android-Studio---IntellJ)

## Features done

* Gradle + AndroidStudio as development Enironment
* Robolectric for unit tests
    * Mockito
    * Code coverage with JaCoCo
    * Code coverage with Teamcity
* Robolectric for component tests
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
* component test coverage on console
* ui test screen shots with spoon, include dialogs
* all nice tools from Quality-Tools-for-Android (pmd, checkstyle, ..)
* test server communication with wire mock (for component test and ui tests)
* generate database content at same time like android annotations
* Travis CI code coverage to Coveralls
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