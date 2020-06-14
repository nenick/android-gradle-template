#!/usr/bin/env bash

SCRIPT_FOLDER=`dirname "$0"`
function run() {
  ./$SCRIPT_FOLDER/run/feature-$1.sh
}

run clean
run unit-tests
run unit-tests-code-coverage
run android-tests
run android-tests-code-coverage
run android-lint
run kotlin-lint