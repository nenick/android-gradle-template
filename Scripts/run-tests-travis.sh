#!/bin/bash

./gradlew :build \
          :App:test \
          :App:connectedAndroidTestIntegrationDebug \
          :App:jacocoReport \
          :App:coveralls \
          -PtravisCi -PdisablePreDex
