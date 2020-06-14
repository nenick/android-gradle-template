#!/usr/bin/env bash
source `dirname "$0"`/_feature-functions.sh

task "Android Tests Code Coverage"
gradle jacocoAndroidTestReport

[ ! -e "app/build/reports/coverage/debug/index.html" ] && [ -e "app/build/reports/jacoco/jacocoAndroidTestReport/html/index.html" ]

result "$?" ""