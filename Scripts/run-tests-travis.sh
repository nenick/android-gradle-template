#!/bin/bash

./gradlew :build \
          :App:test \
          :App:connectedAndroidTestIntegrationDebug \
          :App:mergedData \
          :App:jacocoReport \
          :App:coveralls \
          -PtravisCi -PdisablePreDex
