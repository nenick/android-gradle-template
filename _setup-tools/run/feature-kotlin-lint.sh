#!/usr/bin/env bash
source `dirname "$0"`/_functions.sh

task "Kotlin Lint"
gradle ktLintCheck

BUILD_FOLDERS=`find */* -d -name "build" -not -path "*buildSrc*"`
MISSING_MODULE_REPORT=$(
  for MODULE in $BUILD_FOLDERS
  do
      if [ ! -e "$MODULE/reports/ktlint/ktlintMainSourceSetCheck.html" ] && [ ! -e "$MODULE/reports/ktlint/ktlintTestSourceSetCheck.html" ] && [ ! -e "$MODULE/reports/ktlint/ktlintKotlinScriptCheck.html" ]
      then
          echo "$MODULE"
      fi
  done
)



[ -z "$MISSING_MODULE_REPORT" ]

result "$?" "$MISSING_MODULE_REPORT"