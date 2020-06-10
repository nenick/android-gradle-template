#!/usr/bin/env bash
source `dirname "$0"`/_functions.sh

task "Android Tests"
gradle connectedCheck

[ $? -eq 0 ]

result "$?" ""