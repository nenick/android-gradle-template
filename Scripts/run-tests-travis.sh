#!/bin/bash

./gradlew :UnitTestsRobolectric:testDebug \
          :ComponentTestsRobolectric:testDebug \
          :AndroidSample:connectedAndroidTest \
          :UnitTestsRobolectric:jacocoTestReport

pwd
ls UnitTestsRobolectric/build/reports/
ls UnitTestsRobolectric/build/reports/jacoco/
ls UnitTestsRobolectric/build/reports/jacoco/test/

./gradlew :UnitTestsRobolectric:coveralls -d