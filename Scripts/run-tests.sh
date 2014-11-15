#!/bin/bash

# avoid virtual desktop jumps at mac terminal on test run
export JAVA_TOOL_OPTIONS='-Djava.awt.headless=true'

./gradlew :App:testNormalDebug \
          :App:jacocoReport \
          :App:coveralls \
          :App:testIntegrationDebug \
          :App:connectedCheck

echo "test reports: $(pwd)/build/index.html"