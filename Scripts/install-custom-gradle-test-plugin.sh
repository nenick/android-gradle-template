#!/bin/bash

mkdir temp
cd temp

  git clone https://github.com/novoda/gradle-android-test-plugin.git
  cd gradle-android-test-plugin
  
    git checkout 6fcc8c8c2afc842a9e85303c62348f0bf5323971

    echo "rootProject.name = 'gradle-android-test-plugin-parent'" > settings.gradle
    echo "include ':gradle-android-test-plugin'" >> settings.gradle

    ./gradlew :gradle-android-test-plugin:install

  cd ..
cd ..
