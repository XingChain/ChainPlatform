#!/bin/sh
if [ -x jre/bin/java ]; then
    JAVA=./jre/bin/java
else
    JAVA=java
fi
${JAVA} -cp classes:lib/*:conf:addons/classes:addons/lib/* -Dcid.runtime.mode=desktop -Dcid.runtime.dirProvider=cid.env.DefaultDirProvider cid.Cid
