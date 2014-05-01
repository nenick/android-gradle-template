#!/bin/bash

./gradlew :UnitTestsRobolectric:clean :UnitTestsRobolectric:testDebug :UnitTestsRobolectric:jacocoTestReport


pwd
ls UnitTestsRobolectric/build/reports/
ls UnitTestsRobolectric/build/reports/jacoco/
ls UnitTestsRobolectric/build/reports/jacoco/test/

cp UnitTestsRobolectric/build/jacoco/testDebug.exec UnitTestsRobolectric/build/reports/jacoco/test/.
mkdir -p UnitTestsRobolectric/src/main
mkdir -p UnitTestsRobolectric/src/main/java
cp -r AndroidSample/src/main/java/* UnitTestsRobolectric/src/main/java
ls UnitTestsRobolectric/src/main/java
ls UnitTestsRobolectric/src/main/java/com/example



./gradlew :UnitTestsRobolectric:coveralls -i