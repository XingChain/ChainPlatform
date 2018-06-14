#!/bin/sh
java -cp "classes:lib/*:conf" cid.tools.SignTransactionJSON $@
exit $?
