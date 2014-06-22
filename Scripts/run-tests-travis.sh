#!/bin/bash

Scripts/install-custom-gradle-test-plugin.sh

./gradlew :build \
          :UnitTestsRobolectric:testDebug \
          :ComponentTestsRobolectric:testDebug \
          :AndroidSample:connectedAndroidTest \
          :UnitTestsRobolectric:jacocoTestReport \
          :UnitTestsRobolectric:coveralls \
          -PtravisCi -PdisablePreDex
