#!/bin/bash

$(
    mkdir temp
    cd temp
    git clone https://github.com/nenick/gradle-android-test-plugin.git
    cd android-gradle-template
    ./gradlew :gradle-android-test-plugin:install
)