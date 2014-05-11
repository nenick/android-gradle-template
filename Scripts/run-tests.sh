#!/bin/bash

# avoid virtual desktop jumps at mac terminal on test run
export JAVA_TOOL_OPTIONS='-Djava.awt.headless=true'

./gradlew :UnitTestsRobolectric:testDebug \
          :UnitTestsRobolectric:jacocoTestReport \
          :ComponentTestsRobolectric:testDebug \
          :ComponentTestsRobolectric:jacocoTestReport \
          :AndroidSample:connectedCheck

echo "test reports: $(pwd)/build/index.html"