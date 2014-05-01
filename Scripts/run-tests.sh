#!/bin/bash

./gradlew :UnitTestsRobolectric:testDebug \
          :UnitTestsRobolectric:jacocoTestReport \
          :ComponentTestsRobolectric:testDebug \
          :AndroidSample:connectedAndroidTest

echo "test reports: $(pwd)/build/index.html"