#!/bin/bash

# TODO https://help.github.com/articles/checking-out-pull-requests-locally
# maybe next plugin release remove necessary custom version

git clone https://github.com/robolectric/gradle-android-test-plugin.git

mv install-android-test-gradle-plugin.config gradle-android-test-plugin/.git/config
cd gradle-android-test-plugin

./gradlew install
rm -rf gradle-android-test-plugin
