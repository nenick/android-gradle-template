#!/bin/bash

./gradlew :UnitTestsRobolectric:testDebug \
          :UnitTestsRobolectric:jacocoTestReport \
          :ComponentTestsRobolectric:testDebug \
          :ComponentTestsRobolectric:jacocoTestReport \
          :AndroidSample:connectedCheck

echo "test reports: $(pwd)/build/index.html"