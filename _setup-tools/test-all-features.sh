#!/usr/bin/env bash

SCRIPT_FOLDER=`dirname "$0"`
function run() {
  ./$SCRIPT_FOLDER/run/feature-$1.sh
}

./gradlew clean-test
./gradlew ktlint-test
./gradlew jacoco-test