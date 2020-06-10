#!/usr/bin/env bash
source `dirname "$0"`/_functions.sh

task "Unit Tests"
gradle test

[ $? -eq 0 ]

result "$?" ""