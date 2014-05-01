#!/bin/bash

./gradlew :UnitTestsRobolectric:testDebug \
          :ComponentTestsRobolectric:testDebug \
          :AndroidSample:connectedAndroidTest \
          :UnitTestsRobolectric:jacocoTestReport
./gradlew :UnitTestsRobolectric:coveralls -debug