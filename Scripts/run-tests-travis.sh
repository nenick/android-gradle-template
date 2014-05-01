#!/bin/bash

./gradlew :UnitTestsRobolectric:clean :UnitTestsRobolectric:testDebug :UnitTestsRobolectric:jacocoTestReport

# provide a source files for coveralls-gradle-plugin
# necessary to report code coverage to coveralls

#mkdir -p UnitTestsRobolectric/src/main/java
#cp -r AndroidSample/src/main/java/* UnitTestsRobolectric/src/main/java
#cp -r AndroidSample/src/gen/* UnitTestsRobolectric/src/main/java
#cp -r AndroidSample/build/source/apt/debug/* UnitTestsRobolectric/src/main/java
#cp -r AndroidSample/build/source/buildConfig/debug/* UnitTestsRobolectric/src/main/java
#cp -r AndroidSample/build/source/r/debug/* UnitTestsRobolectric/src/main/java

./gradlew :UnitTestsRobolectric:coveralls