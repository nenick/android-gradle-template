#!/bin/bash

#Scripts/install-custom-gradle-test-plugin.sh

./gradlew :build \
          :App:test \
          :App:connectedAndroidTest \
          -PtravisCi -PdisablePreDex

#          :App:jacocoReport \
#          :App:coveralls \
