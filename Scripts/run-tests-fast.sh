#!/bin/bash

./gradlew :UnitTestsRobolectric:testDebug \
          :ComponentTestsRobolectric:testDebug \
          :UnitTestsRobolectric:jacocoTestReport

echo "test reports: $(pwd)/build/index.html"