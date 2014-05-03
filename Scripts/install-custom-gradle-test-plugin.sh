#!/bin/bash

$(
    mkdir temp
    cd temp
    git clone https://github.com/nenick/gradle-android-test-plugin.git
    cd gradle-android-test-plugin
    ./gradlew :gradle-android-test-plugin:install
)