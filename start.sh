#!/bin/sh
APPLICATION="ChainPlatform"
if [ -e ~/.${APPLICATION}/cid.pid ]; then
    PID=`cat ~/.${APPLICATION}/cid.pid`
    ps -p $PID > /dev/null
    STATUS=$?
    if [ $STATUS -eq 0 ]; then
        echo "Cid server already running"
        exit 1
    fi
fi
mkdir -p ~/.${APPLICATION}/
DIR=`dirname "$0"`
cd "${DIR}"
if [ -x jre/bin/java ]; then
    JAVA=./jre/bin/java
else
    JAVA=java
fi
nohup ${JAVA} -cp classes:lib/*:conf:addons/classes:addons/lib/* -Dcid.runtime.mode=desktop cid.Cid > /dev/null 2>&1 &
echo $! > ~/.${APPLICATION}/cid.pid
cd - > /dev/null
