#!/bin/bash

# avoid virtual desktop jumps at mac terminal on test run
export JAVA_TOOL_OPTIONS='-Djava.awt.headless=true'

./gradlew :AppUnitTests:testDebug \
          :AppUnitTests:jacocoTestReport \
          :AppComponentTests:testDebug \
          :AppComponentTests:jacocoTestReport \
          :App:connectedCheck

echo "test reports: $(pwd)/build/index.html"