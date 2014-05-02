#!/bin/bash

./gradlew :UnitTestsRobolectric:testDebug \
          :ComponentTestsRobolectric:testDebug \
          :AndroidSample:connectedAndroidTest \
          :UnitTestsRobolectric:jacocoTestReport \
          :UnitTestsRobolectric:coveralls \
          -Ptravis-ci -PdisablePreDex