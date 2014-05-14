#!/bin/bash

function killCurrentWiremockInstance {
    [ $(ps aux | grep -v grep | grep -c wiremock) -gt 0 ] && \
        kill -9 $(ps aux | grep -v grep | grep "wiremock.*Wiremock" | awk 'NR==1{print $2}') || \
        echo "No wiremock found to kill"
}

killCurrentWiremockInstance

if [ "$1" != "kill" ]; then
    java -jar Wiremock/wiremock-1.44-standalone.jar --port 9999 --https-port 5432 --verbose --root-dir 'Wiremock' &
else
    echo "only kill servers"
fi
