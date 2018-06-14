#!/bin/sh
CP="conf/;classes/;lib/*;testlib/*"
SP="src/java/;test/java/"
TESTS="cid.crypto.Curve25519Test cid.crypto.ReedSolomonTest"

/bin/rm -f cid.jar
/bin/rm -rf classes
/bin/mkdir -p classes/

javac -encoding utf8 -sourcepath $SP -classpath $CP -d classes/ src/java/cid/*.java src/java/cid/*/*.java test/java/cid/*/*.java || exit 1

java -classpath $CP org.junit.runner.JUnitCore $TESTS

