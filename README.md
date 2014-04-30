***Wishes, improvements and discussions about the stuff here are welcome***

# Template for android app development
Template show different ways for testing.
Should show some common ui examples and how to test them.

## Getting Started

clone, import in idea, start developing.

**unit tests:** gradlew :UnitTestsRobolectric:testDebug

**component tests:** gradlew :ComponentTestsRobolectric:testDebug

**acceptance tests:** gradlew :AndroidSample:connectedAndroidTest

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

## Idea Support

**unit tests** choose test and use "junit" as runner config

**component tests** choose test and use "junit" as runner config

***initial and after each dependency change you need to recreate custom classpath***

1. run random unit test
2. copy shown classpath on console output
3. modify classpath
    1. junit 4 path must be moved as first in classpath
    2. append test-classes path at end of classpath
4. use modified classpath in your default junit run configuration
now you can run unit test like you know it

**ui tests** choose test and use "android test" as runner config

## Features done

* work with idea / android studio
* unit tests
    * inside jvm
    * mockito
    * coverage on teamcity
* component tests
    * inside jvm
    * coverage on teamcity
* android fest assertions
* ui testing with espresso
* generate dependency injection with [AndroidAnnotations](http://androidannotations.org/)
* generate database management with [RoboCop](https://github.com/mediarain/RoboCoP)
* jump between test and implementation with default short cut

## Features wish

* unit test coverage on console
* component test coverage on console
* component test in own module
* ui test coverage
* ui test screen shots with spoon, include dialog
* android annotation
* split unit and com.example.component test run on cmd line
* how to use yml for sdk setup
* all nice tools from Quality-Tools-for-Android (pmd, checkstyle, ..)
* test server communication with wire mock (for component test and ui tests)
* generate database content at same time like android annotations

## Scripts

* run-tests.sh execute all test variants

## Project structure

AndroidSample

* src/main/java/com/example/
    * **activities** activity classes
    * **adapters** data adapters with cursor loader
    * **fragments** fragment classes
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

## Why I don't use the linked projects?

I like having the possibility to run robolectric in a separated module, details read test variants.

deckard-gradle has a disadvantage in compile time. Dependencies for robolectric also compiled for
espresso tests which results in long compile times because of dex incompatible libraries.

Quality-Tools-for-Android contains to much mix up of maven/gradle.