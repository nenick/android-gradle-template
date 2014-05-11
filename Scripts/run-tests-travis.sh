#!/bin/bash

Scripts/install-custom-gradle-test-plugin.sh

./gradlew :UnitTestsRobolectric:testDebug \
          :ComponentTestsRobolectric:testDebug \
          :AndroidSample:connectedAndroidTest \
          :UnitTestsRobolectric:jacocoTestReport \
          :UnitTestsRobolectric:coveralls \
          -PtravisCi -PdisablePreDex

# ensure that a basic build will work too
./gradlew build