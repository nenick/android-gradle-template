#!/bin/bash

./gradlew :build \
          :AppUnitTests:testDebug \
          :AppComponentTests:testDebug \
          :App:connectedAndroidTest \
          :AppUnitTests:jacocoTestReport \
          :AppUnitTests:coveralls \
          -PtravisCi -PdisablePreDex
