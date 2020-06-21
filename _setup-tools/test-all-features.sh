#!/usr/bin/env bash

SCRIPT_FOLDER=`dirname "$0"`
function run() {
  ./$SCRIPT_FOLDER/run/feature-$1.sh
}

./gradlew clean
./gradlew clean-test

run unit-tests
run unit-tests-code-coverage
run android-tests
run android-tests-code-coverage
run android-lint

./gradlew ktlintCheck
./gradlew ktlint-test