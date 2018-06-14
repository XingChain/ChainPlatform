#!/bin/sh
APPLICATION="ChainPlatform"
if [ -e ~/.${APPLICATION}/cid.pid ]; then
    PID=`cat ~/.${APPLICATION}/cid.pid`
    ps -p $PID > /dev/null
    STATUS=$?
    echo "stopping"
    while [ $STATUS -eq 0 ]; do
        kill `cat ~/.${APPLICATION}/cid.pid` > /dev/null
        sleep 5
        ps -p $PID > /dev/null
        STATUS=$?
    done
    rm -f ~/.${APPLICATION}/cid.pid
    echo "Cid server stopped"
fi

