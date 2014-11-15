#!/bin/bash

./gradlew :build \
          :App:testNormalDebug \
          :App:jacocoReport \
          :App:coveralls \
          :App:testIntegrationDebug \
          :App:connectedAndroidTestIntegrationDebug \
          -PtravisCi -PdisablePreDex
