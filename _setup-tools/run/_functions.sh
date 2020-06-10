function task() {
    echo -n "* $1 ... "
}

function gradle() {
    ./gradlew $* > /dev/null
}

function result() {
    CHECK_RESULT=$1
    RESULT_REASONS=$2

    if [ "$CHECK_RESULT" == "0" ]
    then
        echo "success"
    else
        echo "failed"
    fi

    if [ ! -z "$RESULT_REASONS" ]
    then
        echo "    detected reason:"
        echo "$RESULT_REASONS" | while read line; do
          echo "    - $line"
        done
    fi
}
