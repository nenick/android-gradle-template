#!/usr/bin/env bash
source `dirname "$0"`/_functions.sh

task "Unit Tests Code Coverage"
gradle jacocoUnitTestReport jacocoTestReport

BUILD_FOLDERS=`find */* -d -name "build" -not -path "*buildSrc*"`
MISSING_MODULE_REPORT=$(
  for MODULE in $BUILD_FOLDERS
  do
      if [ ! -e "$MODULE/reports/jacoco/jacocoUnitTestReport/html/index.html" ] && [ ! -e "$MODULE/reports/jacoco/test/html/index.html" ]
      then
          echo "$MODULE"
      fi
  done
)
[ -z "$MISSING_MODULE_REPORT" ]

result "$?" "$MISSING_MODULE_REPORT"