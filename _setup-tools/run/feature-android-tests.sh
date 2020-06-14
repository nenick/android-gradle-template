#!/usr/bin/env bash
source `dirname "$0"`/_feature-functions.sh

task "Android Tests"
gradle connectedDebugAndroidTest

[ $? -eq 0 ]

result "$?" ""