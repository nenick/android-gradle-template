#!/usr/bin/env bash

# import some helper functions
source `dirname "$0"`/_feature-functions.sh

task "Clean Project"
gradle clean

# check all build folders are removed
BUILD_FOLDERS=`find . -d -name "build"`
[ -z "$BUILD_FOLDERS" ]

# print formated result
result "$?" "$BUILD_FOLDERS"