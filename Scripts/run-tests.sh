#!/bin/bash

./gradlew :UnitTestsRobolectric:testDebug \
          :UnitTestsRobolectric:jacocoTestReport \
          :ComponentTestsRobolectric:testDebug \
          :AndroidSample:connectedCheck

echo "test reports: $(pwd)/build/index.html"