#!/bin/bash

./gradlew :UnitTestsRobolectric:testDebug \
          :UnitTestsRobolectric:jacocoTestReport

pwd
ls UnitTestsRobolectric/build/reports/
ls UnitTestsRobolectric/build/reports/jacoco/
ls UnitTestsRobolectric/build/reports/jacoco/test/

./gradlew :UnitTestsRobolectric:coveralls -d