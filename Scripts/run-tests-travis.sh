#!/bin/bash

Scripts/install-custom-gradle-test-plugin.sh

./gradlew :build \
          :AppUnitTests:testDebug \
          :AppComponentTests:testDebug \
          :App:connectedAndroidTest \
          :AppUnitTests:jacocoTestReport \
          :AppUnitTests:coveralls \
          -PtravisCi -PdisablePreDex
