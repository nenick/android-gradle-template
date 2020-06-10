#!/usr/bin/env bash
source `dirname "$0"`/_functions.sh

task "Android Tests Code Coverage"
gradle connectedCheck

[ -e "app/build/reports/coverage/debug/index.html" ]

result "$?" ""