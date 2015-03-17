#!/bin/bash

Scripts/install-custom-gradle-test-plugin.sh

./gradlew :build \
          :App:testDebug \
          :AppComponentTests:testDebug \
          :App:connectedAndroidTest \
          :App:jacocoTestReport \
          :App:coveralls \
          -PtravisCi -PdisablePreDex
