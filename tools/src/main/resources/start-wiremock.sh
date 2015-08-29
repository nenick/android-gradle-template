#!/bin/bash

function killCurrentWiremockInstance {


    [ $(ps aux | grep -v grep | grep -c "wiremock.*standalone") -gt 0 ] && \
        kill -9 $(ps aux | grep -v grep | grep "wiremock.*standalone" | awk 'NR==1{print $2}') || \
        echo "No wiremock found to kill"
}

killCurrentWiremockInstance

if [ "$1" != "kill" ]; then
    java -jar wiremock/wiremock-1.57-standalone.jar --port 1337 --https-port 1338 --verbose --root-dir 'wiremock/src/main/resources' &
else
    echo "only kill servers"
fi