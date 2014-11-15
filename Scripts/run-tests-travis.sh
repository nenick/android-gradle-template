#!/bin/bash

#Scripts/install-custom-gradle-test-plugin.sh

./gradlew :build \
          :App:test \
          :App:connectedAndroidTest \
          :App:jacocoReport \
          :App:coveralls \
          -PtravisCi -PdisablePreDex
