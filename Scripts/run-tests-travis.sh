#!/bin/bash

./gradlew :UnitTestsRobolectric:clean :UnitTestsRobolectric:testDebug :UnitTestsRobolectric:jacocoTestReport -i


pwd
ls UnitTestsRobolectric/build/reports/
ls UnitTestsRobolectric/build/reports/jacoco/
ls UnitTestsRobolectric/build/reports/jacoco/test/

./gradlew :UnitTestsRobolectric:coveralls -d