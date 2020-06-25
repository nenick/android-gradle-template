#!/usr/bin/env bash

# quick hack to support full coverage report on azure pipelines

mkdir -p build/coverage/sources
cp -r buildSrc/src/main/kotlin/* build/coverage/sources
cp -r app/src/main/java/* build/coverage/sources
cp -r core-data-network/src/main/java/* build/coverage/sources