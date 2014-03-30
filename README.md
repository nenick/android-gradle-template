# Professional Android App Template
Template which enables some stuff for more advanced android app development.

## Getting Started

clone, import in idea, start developing.

**unit tests:** gradlew :android-sample:testDebug

**com.example.component tests:** gradlew :android-sample:testDebug

**ui tests:** gradlew :espresso:connectedAndroidTest

## Idea Support

**unit tests** choose test and use "junit" as runner config

**com.example.component tests** choose test and use "junit" as runner config

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
* com.example.component tests
    * inside jvm
    * coverage on teamcity
* android fest assertions
* ui testing with espresso


## Features wish

* unit test coverage on console
* com.example.component test coverage on console
* ui test coverage
* ui test screen shots with spoon, include dialog
* android annotation
* split unit and com.example.component test run on cmd line
* how to use yml for sdk setup
* all nice tools from Quality-Tools-for-Android
* jump to all test (cmd + shift + t) actual only ui tests are shown, looks like espresso path config destroy this feature

## Scripts

* install-android-test-gradle-plugin-snapshot.sh build the necessary plugin version
* .travis.yml sdk setup


## See also my inspiration sources

[Quality-Tools-for-Android](https://github.com/stephanenicolas/Quality-Tools-for-Android)

[deckard-gradle](https://github.com/robolectric/deckard-gradle)