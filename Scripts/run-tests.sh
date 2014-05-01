#!/bin/bash

./gradlew :UnitTestsRobolectric:testDebug \
          :ComponentTestsRobolectric:testDebug \
          :AndroidSample:connectedAndroidTest

echo "test reports: $(pwd)/build/index.html"