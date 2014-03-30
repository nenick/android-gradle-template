# Template for android app development
Template show different ways for testing.
Should show some common ui examples and how to test them.

## Getting Started

clone, import in idea, start developing.

**unit tests:** gradlew :robolectric:testDebug

**component tests:** gradlew :robolectric:testDebug

**ui tests:** gradlew :android-sample:connectedAndroidTest

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
* component and ui test looks very equal for fast test developing
* dependency injection with android annotations
* jump between test and implementation with default short cut

## Features wish

* unit test coverage on console
* component test coverage on console
* ui test coverage
* ui test screen shots with spoon, include dialog
* android annotation
* split unit and com.example.component test run on cmd line
* how to use yml for sdk setup
* all nice tools from Quality-Tools-for-Android (pmd, checkstyle, ..)
* test server communication with wire mock (for component test and ui tests)

## Scripts

* install-android-test-gradle-plugin-snapshot.sh build the necessary plugin version
* .travis.yml sdk setup


## See also my inspiration sources

[Quality-Tools-for-Android](https://github.com/stephanenicolas/Quality-Tools-for-Android)

[deckard-gradle](https://github.com/robolectric/deckard-gradle)