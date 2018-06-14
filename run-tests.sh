#!/bin/sh
CP=conf/:classes/:lib/*:testlib/*
SP=src/java/:test/java/

if [ $# -eq 0 ]; then
TESTS="cid.crypto.Curve25519Test cid.crypto.ReedSolomonTest cid.peer.HallmarkTest cid.TokenTest cid.FakeForgingTest
cid.FastForgingTest cid.ManualForgingTest"
else
TESTS=$@
fi

/bin/rm -f cid.jar
/bin/rm -rf classes
/bin/mkdir -p classes/

javac -encoding utf8 -sourcepath ${SP} -classpath ${CP} -d classes/ src/java/cid/*.java src/java/cid/*/*.java test/java/cid/*.java test/java/cid/*/*.java || exit 1

for TEST in ${TESTS} ; do
java -classpath ${CP} org.junit.runner.JUnitCore ${TEST} ;
done



