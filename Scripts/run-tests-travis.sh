#!/bin/bash

./gradlew :UnitTestsRobolectric:clean :UnitTestsRobolectric:testDebug :UnitTestsRobolectric:jacocoTestReport -i


pwd
ls UnitTestsRobolectric/build/reports/
ls UnitTestsRobolectric/build/reports/jacoco/
ls UnitTestsRobolectric/build/reports/jacoco/test/

cp UnitTestsRobolectric/build/jacoco/testDebug.exec UnitTestsRobolectric/build/reports/jacoco/test/.
mkdir -p UnitTestsRobolectric/src/main
cp -r AndroidSample/src/main UnitTestsRobolectric/src/main

./gradlew :UnitTestsRobolectric:coveralls -d