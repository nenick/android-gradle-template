#!/usr/bin/env bash
source `dirname "$0"`/_feature-functions.sh

task "Unit Tests"
gradle test

BUILD_FOLDERS=`find */* -d -name "build" -not -path "*buildSrc*"`
MISSING_MODULE_REPORT=$(
  for MODULE in $BUILD_FOLDERS
  do
      if [ ! -e "$MODULE/reports/tests/test/index.html" ] && [ ! -e "$MODULE/reports/tests/testDebugUnitTest/index.html" ]
      then
          echo "$MODULE"
      fi
      if [ -e "$MODULE/reports/tests/testReleaseUnitTest/index.html" ]
      then
          echo "$MODULE/reports/tests/testReleaseUnitTest/index.html"
      fi
  done
)



[ -z "$MISSING_MODULE_REPORT" ]

result "$?" "$MISSING_MODULE_REPORT"