#!/usr/bin/env bash
source `dirname "$0"`/_feature-functions.sh

task "Android Lint"
gradle lintDebug

BUILD_FOLDERS=`find */* -d -name "build" -not -path "*buildSrc*"`
MISSING_MODULE_REPORT=$(
  for MODULE in $BUILD_FOLDERS
  do
      IS_ANDROID=`grep "application" $MODULE/../build.gradle.kts`
      if [ ! -z "$IS_ANDROID" ] && [ ! -e "$MODULE/reports/lint-results-debug.html" ]
      then
          echo "$MODULE"
      fi
      if [ -e "$MODULE/reports/lint-results-release.html" ]
      then
          echo "$MODULE/reports/lint-results-release.html"
      fi
  done
)



[ -z "$MISSING_MODULE_REPORT" ]

result "$?" "$MISSING_MODULE_REPORT"